package dev.shashiirk.shopmate.security;

import dev.shashiirk.shopmate.config.AppProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component class responsible for JWT token generation, validation, and authentication.
 */
@Slf4j
@Component
public class TokenProvider {

    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    public TokenProvider(AppProperties appProperties) {
        AppProperties.Security.Jwt jwtProperties = appProperties
                .getSecurity()
                .getJwt();
        String jwtSecret = jwtProperties.getSecret();
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = jwtProperties.getTokenValidityInMilliSeconds();
    }

    /**
     * Creates a JWT token for the specified authentication.
     *
     * @param authentication Authentication object representing the authenticated user
     * @return String containing the JWT token
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(ClaimKeys.AUTHORITIES.getValue(), authorities)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts authentication information from the JWT token.
     *
     * @param token String representing the JWT token
     * @return Authentication object representing the authenticated user
     */
    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> authorities = Arrays
                .stream(claims.get(ClaimKeys.AUTHORITIES.getValue()).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .toList();

        User user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    /**
     * Validates the integrity and expiration of the JWT token.
     *
     * @param token String representing the JWT token
     * @return true if the token is valid; false otherwise
     */
    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT Token", e);
            return false;
        }
    }

    /**
     * Enumeration representing keys used in JWT claims.
     */
    @Getter
    @AllArgsConstructor
    private enum ClaimKeys {
        AUTHORITIES("auth");

        private final String value;
    }

}
