package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Role} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private String name;

}
