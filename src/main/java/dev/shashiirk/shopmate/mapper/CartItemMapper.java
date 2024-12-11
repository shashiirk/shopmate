package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.dto.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link CartItem} and its DTO {@link CartItemDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {
}
