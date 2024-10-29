package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.dto.CartItemDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CartItem} and its DTO {@link CartItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {
}
