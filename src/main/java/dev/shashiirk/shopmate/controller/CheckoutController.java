package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.dto.CartActionDTO;
import dev.shashiirk.shopmate.dto.CartResponseDTO;
import dev.shashiirk.shopmate.dto.PlaceOrderRequestDTO;
import dev.shashiirk.shopmate.dto.PlaceOrderResponseDTO;
import dev.shashiirk.shopmate.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing checkout related operations.
 */
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     * Retrieves the active cart details of the current user.
     *
     * @return The ResponseEntity with status 200 (OK) and with body the CartProductsResponseDTO.
     */
    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> getCartDetails() {
        CartResponseDTO cartResponse = checkoutService.getCartDetails();
        return ResponseEntity.ok().body(cartResponse);
    }

    /**
     * Adds a product to the active cart of the current user.
     *
     * @param cartActionDTO The cartActionDTO to add the product.
     * @return The ResponseEntity with status 200 (OK) and with body the CartProductsResponseDTO.
     */
    @PostMapping("/cart/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestBody CartActionDTO cartActionDTO) {
        CartResponseDTO cartResponse = checkoutService.addToCart(cartActionDTO);
        return ResponseEntity.ok().body(cartResponse);
    }

    /**
     * Updates the cart item in active cart of the current user.
     *
     * @param cartActionDTO The cartActionDTO to update the cart item.
     * @return The ResponseEntity with status 200 (OK) and with body the CartProductsResponseDTO.
     */
    @PatchMapping("/cart/update")
    public ResponseEntity<CartResponseDTO> updateCart(@RequestBody CartActionDTO cartActionDTO) {
        CartResponseDTO cartResponse = checkoutService.updateCart(cartActionDTO);
        return ResponseEntity.ok().body(cartResponse);
    }

    /**
     * Removes a product from the active cart of the current user.
     *
     * @param cartActionDTO The cartActionDTO to remove a product.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @PostMapping("/cart/remove")
    public ResponseEntity<Void> removeFromCart(@RequestBody CartActionDTO cartActionDTO) {
        checkoutService.removeFromCart(cartActionDTO);
        return ResponseEntity.noContent().build();
    }

    /**
     * Places an order for the current user.
     *
     * @param placeOrderRequestDTO The placeOrderRequestDTO to place the order.
     * @return The ResponseEntity with status 200 (OK) and with body the PlaceOrderResponseDTO.
     */
    @PostMapping("/order/place")
    public ResponseEntity<PlaceOrderResponseDTO> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO) {
        PlaceOrderResponseDTO placeOrderResponseDTO = checkoutService.placeOrder(placeOrderRequestDTO);
        return ResponseEntity.ok().body(placeOrderResponseDTO);
    }

}
