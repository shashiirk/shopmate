package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.dto.*;
import dev.shashiirk.shopmate.enumeration.CartItemStatus;
import dev.shashiirk.shopmate.enumeration.CartStatus;
import dev.shashiirk.shopmate.enumeration.OrderStatus;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.exception.OperationFailedException;
import dev.shashiirk.shopmate.exception.UnauthorizedException;
import dev.shashiirk.shopmate.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service implementation for checkout operations.
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final AuthServiceImpl authService;

    private final CartService cartService;

    private final CartItemService cartItemService;

    private final ProductService productService;

    private final OrderSummaryService orderSummaryService;

    public CheckoutServiceImpl(
            AuthServiceImpl authService,
            CartService cartService,
            CartItemService cartItemService,
            ProductService productService,
            OrderSummaryService orderSummaryService
    ) {
        this.authService = authService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.orderSummaryService = orderSummaryService;
    }

    @Override
    public CartResponseDTO getCartDetails() {
        List<CartItemDTO> currentCartItems = cartService.getCurrentCartItems();

        return CartResponseDTO
                .builder()
                .cartItems(currentCartItems)
                .build();
    }

    @Override
    public CartResponseDTO addToCart(CartActionDTO cartActionDTO) {
        Long productId = cartActionDTO.getProductId();
        ProductDTO product = productService.findOne(productId)
                                           .orElseThrow(() -> new NotFoundException("Product not found"));
        CartDTO currentCart = cartService.getCurrentCart();
        cartService.getCartItems(currentCart.getId())
                   .stream()
                   .filter(item -> item.getProduct().getId().equals(productId) &&
                                   item.getStatus() == CartItemStatus.ADDED)
                   .findFirst()
                   .ifPresentOrElse(
                           item -> {
                               throw new NotFoundException("Product already exists in cart");
                           },
                           () -> {
                               CartItemDTO cartItemDTO = CartItemDTO.builder()
                                                                    .cart(currentCart)
                                                                    .product(product)
                                                                    .quantity(cartActionDTO.getQuantity())
                                                                    .status(CartItemStatus.ADDED)
                                                                    .amount(product.getSellingPrice())
                                                                    .build();
                               cartItemService.save(cartItemDTO);
                           }
                   );

        return getCartDetails();
    }

    @Override
    public CartResponseDTO updateCart(CartActionDTO cartActionDTO) {
        Long productId = cartActionDTO.getProductId();
        if (!productService.existsById(productId)) {
            throw new NotFoundException("Product not found");
        }

        // Update the quantity of the product in the cart
        cartService.getCurrentCartItems()
                   .stream()
                   .filter(cartItemDTO -> cartItemDTO.getProduct().getId().equals(productId))
                   .findFirst()
                   .ifPresentOrElse(
                           item -> {
                               if (cartActionDTO.getQuantity() != null && cartActionDTO.getQuantity() > 0) {
                                   item.setQuantity(cartActionDTO.getQuantity());
                               }
                               if (cartActionDTO.getAmount() != null) {
                                   item.setAmount(cartActionDTO.getAmount());
                               }
                               cartItemService.save(item);
                           },
                           () -> {
                               throw new NotFoundException("Product not found in cart");
                           }
                   );

        return getCartDetails();
    }

    @Override
    public void removeFromCart(CartActionDTO cartActionDTO) {
        Long productId = cartActionDTO.getProductId();
        if (!productService.existsById(productId)) {
            throw new NotFoundException("Product not found");
        }

        // Update the cart item status
        cartService.getCurrentCartItems()
                   .stream()
                   .filter(cartItemDTO -> cartItemDTO.getProduct().getId().equals(productId))
                   .findFirst()
                   .ifPresentOrElse(
                           item -> {
                               item.setStatus(CartItemStatus.REMOVED);
                               cartItemService.save(item);
                           },
                           () -> {
                               throw new NotFoundException("Product not found in cart");
                           }
                   );
    }

    @Override
    public PlaceOrderResponseDTO placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
        UserDTO currentUser = authService.getCurrentUser().orElseThrow(UnauthorizedException::new);

        Long cartId = placeOrderRequestDTO.getCartId();
        CartDTO placeOrderCart = cartService.findOne(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));
        if (placeOrderCart.getStatus().equals(CartStatus.ORDERED)) {
            throw new BadRequestException("Cart already ordered");
        }

        CartDTO currentCart = cartService.getCurrentCart();
        if (!currentCart.getId().equals(cartId)) {
            throw new BadRequestException("Invalid cart");
        }

        try {
            AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);
            List<OrderItemDTO> orderItems = cartService
                    .getCurrentCartItems()
                    .stream()
                    .map(cartItem -> {
                        double amount = cartItem.getAmount() * cartItem.getQuantity();
                        totalAmount.updateAndGet(v -> v + amount);

                        return OrderItemDTO
                                .builder()
                                .product(cartItem.getProduct())
                                .quantity(cartItem.getQuantity())
                                .amount(amount)
                                .build();
                    })
                    .toList();

            OrderSummaryDTO order = OrderSummaryDTO
                    .builder()
                    .orderId(orderSummaryService.generateOrderId())
                    .cart(currentCart)
                    .user(currentUser)
                    .orderItems(orderItems)
                    .totalAmount(totalAmount.get())
                    .status(OrderStatus.PAID)
                    .build();

            OrderSummaryDTO savedOrder = orderSummaryService.save(order);

            return PlaceOrderResponseDTO
                    .builder()
                    .orderId(savedOrder.getOrderId())
                    .build();
        } catch (Exception e) {
            throw new OperationFailedException("Failed to place order");
        }

    }

}
