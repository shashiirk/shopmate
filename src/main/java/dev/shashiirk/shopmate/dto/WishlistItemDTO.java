package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.WishlistItem;
import dev.shashiirk.shopmate.enumeration.WishlistItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link WishlistItem} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemDTO {

    private Long id;

    private WishlistItemStatus status;

    private WishlistDTO wishlist;

    private ProductDTO product;

}
