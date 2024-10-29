package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.dto.UserLoginRequestDTO;
import dev.shashiirk.shopmate.dto.UserRegistrationRequestDTO;
import dev.shashiirk.shopmate.dto.AuthenticationResponseDTO;
import dev.shashiirk.shopmate.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling authentication requests.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     *
     * @param request the request containing user registration details
     * @return an authentication response containing the result of the registration process
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> registerUser(
            @Valid @RequestBody UserRegistrationRequestDTO request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    /**
     * Authenticate and login a user.
     *
     * @param request the request containing user login credentials
     * @return an authentication response containing the result of the login process
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@Valid @RequestBody UserLoginRequestDTO request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }
}
