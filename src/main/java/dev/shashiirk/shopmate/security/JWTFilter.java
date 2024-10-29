package dev.shashiirk.shopmate.security;

import dev.shashiirk.shopmate.context.AppContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Component class responsible for JWT token-based authentication.
 * This class extends Spring Security's OncePerRequestFilter to intercept and process authentication for each request.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Processes the authentication for each HTTP request.
     * Validates and extracts JWT token from the request, sets the authentication in the SecurityContextHolder if valid.
     *
     * @param request     HttpServletRequest object representing the HTTP request
     * @param response    HttpServletResponse object representing the HTTP response
     * @param filterChain FilterChain object to invoke the next filter in the chain
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException      if an I/O error occurs while handling the request or response
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            AppContextHolder.clearContext(); // Clear the context after processing the request
        }
    }

    /**
     * Extract bearer token from authorization header.
     *
     * @param request HttpServletRequest object representing the HTTP request
     * @return String representing the resolved JWT token, or null if not found
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
