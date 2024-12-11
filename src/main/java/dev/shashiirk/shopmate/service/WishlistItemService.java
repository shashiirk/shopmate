package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.WishlistItem;
import dev.shashiirk.shopmate.dto.WishlistItemDTO;

import java.util.List;

/**
 * Service interface for managing {@link WishlistItem}.
 */
public interface WishlistItemService extends EntityService<WishlistItemDTO> {

    /**
     * Find all wishlist items by wishlist ID.
     *
     * @param wishlistId The ID of the wishlist.
     * @return The list of wishlist items.
     */
    List<WishlistItemDTO> findAllActiveItemsByWishlistId(Long wishlistId);

}
