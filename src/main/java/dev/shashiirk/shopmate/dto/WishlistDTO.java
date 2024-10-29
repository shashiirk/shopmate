package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Wishlist} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {

    private Long id;

    private UserDTO user;

}
