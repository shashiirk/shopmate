package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Order;
import dev.shashiirk.shopmate.dto.OrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
}
