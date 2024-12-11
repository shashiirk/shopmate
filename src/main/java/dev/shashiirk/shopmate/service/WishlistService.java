package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.Wishlist;
import dev.shashiirk.shopmate.dto.*;

/**
 * Service interface for managing {@link Wishlist}.
 */
public interface WishlistService extends EntityService<WishlistDTO> {

    /**
     * Get the products in the current wishlist.
     *
     * @return the wishlist response
     */
    WishlistResponseDTO getProducts();

    /**
     * Add a product to the current wishlist.
     *
     * @param wishlistActionDTO the item to add
     * @return the updated wishlist
     */
    WishlistResponseDTO addProduct(WishlistActionDTO wishlistActionDTO);

    /**
     * Remove a product from the current wishlist.
     *
     * @param wishlistActionDTO the item to remove
     */
    void removeProduct(WishlistActionDTO wishlistActionDTO);

}
