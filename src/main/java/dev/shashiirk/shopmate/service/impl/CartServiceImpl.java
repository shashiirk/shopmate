package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.dto.CartDTO;
import dev.shashiirk.shopmate.dto.CartItemDTO;
import dev.shashiirk.shopmate.dto.UserDTO;
import dev.shashiirk.shopmate.enumeration.CartStatus;
import dev.shashiirk.shopmate.exception.UnauthorizedException;
import dev.shashiirk.shopmate.mapper.CartMapper;
import dev.shashiirk.shopmate.repository.CartRepository;
import dev.shashiirk.shopmate.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Cart}.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final AuthServiceImpl authService;

    private final CartItemServiceImpl cartItemService;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartMapper cartMapper,
            AuthServiceImpl authService,
            CartItemServiceImpl cartItemService
    ) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.authService = authService;
        this.cartItemService = cartItemService;
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    public Optional<CartDTO> partialUpdate(CartDTO cartDTO) {
        return cartRepository.findById(cartDTO.getId()).map(cart -> {
            cartMapper.partialUpdate(cartDTO, cart);
            return cart;
        }).map(cartRepository::save).map(cartMapper::toDTO);
    }

    @Override
    public Optional<CartDTO> findOne(Long id) {
        return cartRepository.findById(id).map(cartMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public CartDTO getCurrentCart() {
        UserDTO currentUser = authService.getCurrentUser().orElseThrow(UnauthorizedException::new);

        return cartRepository
                .findByUserIdAndStatus(currentUser.getId(), CartStatus.ACTIVE)
                .stream()
                .findFirst()
                .map(cartMapper::toDTO)
                .orElseGet(() -> {
                    CartDTO cartDTO = CartDTO.builder()
                                             .status(CartStatus.ACTIVE)
                                             .user(currentUser)
                                             .build();
                    return save(cartDTO);
                });
    }

    @Override
    public List<CartItemDTO> getCurrentCartItems() {
        CartDTO currentCart = getCurrentCart();
        return getCartItems(currentCart.getId());
    }


    @Override
    public List<CartItemDTO> getCartItems(Long cartId) {
        return cartItemService.findAllActiveItemsByCartId(cartId);
    }

}
