package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.dto.CartItemDTO;
import dev.shashiirk.shopmate.mapper.CartItemMapper;
import dev.shashiirk.shopmate.repository.CartItemRepository;
import dev.shashiirk.shopmate.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CartItem}.
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public CartItemDTO save(CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDTO(cartItem);
    }

    @Override
    public Optional<CartItemDTO> partialUpdate(CartItemDTO cartItemDTO) {
        return cartItemRepository.findById(cartItemDTO.getId()).map(cartItem -> {
            cartItemMapper.partialUpdate(cartItemDTO, cartItem);
            return cartItem;
        }).map(cartItemRepository::save).map(cartItemMapper::toDTO);
    }

    @Override
    public Optional<CartItemDTO> findOne(Long id) {
        return cartItemRepository.findById(id).map(cartItemMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

}
