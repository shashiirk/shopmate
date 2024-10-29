package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.dto.CartDTO;
import dev.shashiirk.shopmate.mapper.CartMapper;
import dev.shashiirk.shopmate.repository.CartRepository;
import dev.shashiirk.shopmate.service.CartService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cart}.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
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

}
