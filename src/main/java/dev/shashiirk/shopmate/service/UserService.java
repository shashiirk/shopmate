package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.User;
import dev.shashiirk.shopmate.dto.UserDTO;

import java.util.Optional;

/**
 * Service interface for managing {@link User}.
 */
public interface UserService extends EntityService<UserDTO> {

    /**
     * Register a new user.
     *
     * @param userDTO the DTO representing the user to register
     * @return the registered user DTO
     */
    UserDTO register(UserDTO userDTO);

    /**
     * Find a user by email.
     *
     * @param email the email of the user to find
     * @return the user DTO
     */
    Optional<UserDTO> findByEmail(String email);

}
