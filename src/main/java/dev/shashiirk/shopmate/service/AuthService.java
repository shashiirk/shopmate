package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.dto.AuthenticationResponseDTO;
import dev.shashiirk.shopmate.dto.UserDTO;
import dev.shashiirk.shopmate.dto.UserLoginRequestDTO;
import dev.shashiirk.shopmate.dto.UserRegistrationRequestDTO;

import java.util.Optional;

/**
 * Service interface for user authentication operations.
 */
public interface AuthService {

    /**
     * Register a new user.
     *
     * @param request the request containing user registration details
     * @return an authentication response containing the result of the registration process
     */
    AuthenticationResponseDTO registerUser(UserRegistrationRequestDTO request);

    /**
     * Authenticate and login a user.
     *
     * @param request the request containing user login credentials
     * @return an authentication response containing the result of the login process
     */
    AuthenticationResponseDTO loginUser(UserLoginRequestDTO request);

    /**
     * Get the current authenticated user.
     *
     * @return the current authenticated user
     */
    Optional<UserDTO> getCurrentUser();

}
