package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.dto.CartItemDTO;

import java.util.List;

/**
 * Service interface for managing {@link CartItem}.
 */
public interface CartItemService extends EntityService<CartItemDTO> {

    List<CartItemDTO> findAllActiveItemsByCartId(Long cartId);

}

