package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.OrderSummary;
import dev.shashiirk.shopmate.dto.OrderSummaryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OrderSummary} and its DTO {@link OrderSummaryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderSummaryMapper extends EntityMapper<OrderSummaryDTO, OrderSummary> {
}
