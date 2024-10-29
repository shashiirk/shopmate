package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.OrderItem;
import dev.shashiirk.shopmate.dto.OrderItemDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
}
