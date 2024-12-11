package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.dto.CartActionDTO;
import dev.shashiirk.shopmate.dto.CartResponseDTO;
import dev.shashiirk.shopmate.dto.PlaceOrderRequestDTO;
import dev.shashiirk.shopmate.dto.PlaceOrderResponseDTO;

/**
 * Service interface for checkout operations.
 */
public interface CheckoutService {

    /**
     * Retrieves the active cart details of the current user.
     *
     * @return The CartResponseDTO with the cart details.
     */
    CartResponseDTO getCartDetails();

    /**
     * Adds a product to the active cart of the current user.
     *
     * @param cartActionDTO The cartActionDTO to add the product.
     * @return The CartResponseDTO with the updated cart details.
     */
    CartResponseDTO addToCart(CartActionDTO cartActionDTO);

    /**
     * Updates the cart item in active cart of the current user.
     *
     * @param cartActionDTO The cartActionDTO to update the cart item.
     * @return The CartResponseDTO with the updated cart details.
     */
    CartResponseDTO updateCart(CartActionDTO cartActionDTO);

    /**
     * Removes a product from the active cart of the current user.
     *
     * @param cartActionDTO The cartActionDTO to remove the product.
     */
    void removeFromCart(CartActionDTO cartActionDTO);

    /**
     * Places an order for the active cart of the current user.
     *
     * @param placeOrderRequestDTO The placeOrderRequestDTO to place the order.
     * @return The PlaceOrderResponseDTO with the order details.
     */
    PlaceOrderResponseDTO placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO);

}
