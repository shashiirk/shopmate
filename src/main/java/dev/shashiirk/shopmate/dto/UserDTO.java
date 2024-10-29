package dev.shashiirk.shopmate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.shashiirk.shopmate.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * A DTO for the {@link User} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    private boolean active;

    private Set<RoleDTO> roles;

}
