package dev.shashiirk.shopmate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.enumeration.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Cart} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long id;

    private CartStatus status;

    @JsonIgnore
    private UserDTO user;

}
