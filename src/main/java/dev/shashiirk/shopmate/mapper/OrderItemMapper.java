package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.OrderItem;
import dev.shashiirk.shopmate.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
}
