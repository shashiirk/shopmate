package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.dto.CartDTO;
import dev.shashiirk.shopmate.dto.CartItemDTO;

import java.util.List;

/**
 * Service interface for managing {@link Cart}.
 */
public interface CartService extends EntityService<CartDTO> {

    /**
     * Get current cart.
     *
     * @return the current cart.
     */
    CartDTO getCurrentCart();

    /**
     * Get current cart items.
     *
     * @return the current cart items.
     */
    List<CartItemDTO> getCurrentCartItems();

    /**
     * Get cart items.
     *
     * @param cartId the cart id.
     * @return the list of cart items.
     */
    List<CartItemDTO> getCartItems(Long cartId);

}
