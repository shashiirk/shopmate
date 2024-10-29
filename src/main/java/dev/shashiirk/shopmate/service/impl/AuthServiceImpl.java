package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.context.AppContextHolder;
import dev.shashiirk.shopmate.dto.*;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.mapper.RoleMapper;
import dev.shashiirk.shopmate.repository.RoleRepository;
import dev.shashiirk.shopmate.security.RoleConstants;
import dev.shashiirk.shopmate.security.TokenProvider;
import dev.shashiirk.shopmate.service.AuthService;
import dev.shashiirk.shopmate.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing user authentication operations.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            RoleMapper roleMapper,
            UserService userService,
            TokenProvider tokenProvider, AuthenticationManager authenticationManager
    ) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponseDTO registerUser(UserRegistrationRequestDTO request) {
        UserDTO userDTO = UserDTO.builder()
                                 .firstName(Optional.ofNullable(request.getFirstName())
                                                    .map(String::trim)
                                                    .orElse(null))
                                 .lastName(Optional.ofNullable(request.getLastName())
                                                   .map(String::trim)
                                                   .orElse(null))
                                 .email(request.getEmail())
                                 .password(passwordEncoder.encode(request.getPassword()))
                                 .active(true)
                                 .build();
        Set<RoleDTO> roles = new HashSet<>();
        roleRepository.findById(RoleConstants.USER).map(roleMapper::toDTO).ifPresent(roles::add);
        userDTO.setRoles(roles);
        userDTO = userService.register(userDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRoles()
                       .stream()
                       .map(roleDTO -> new SimpleGrantedAuthority(roleDTO.getName()))
                       .toList());
        String token = tokenProvider.createToken(authentication);

        return AuthenticationResponseDTO.builder().authToken(token).build();
    }

    @Override
    public AuthenticationResponseDTO loginUser(UserLoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.createToken(authentication);
            return AuthenticationResponseDTO.builder().authToken(token).build();
        } catch (AuthenticationException e) {
            throw new BadRequestException("Authentication failed. Please check your credentials.");
        }
    }

    @Override
    public Optional<UserDTO> getCurrentUser() {
        AppContextDTO context = AppContextHolder.getContext();

        if (context.getUser() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                context.setAuthenticated(false);
                context.setUser(null);
            } else if (authentication.getPrincipal() instanceof UserDetails principal) {
                String email = principal.getUsername();
                Optional<UserDTO> user = userService.findByEmail(email);

                // Set the user in the context if found
                user.ifPresent(userDTO -> {
                    context.setAuthenticated(true);
                    context.setUser(userDTO);
                });
            }
        }

        return Optional.ofNullable(context.getUser());
    }

}
