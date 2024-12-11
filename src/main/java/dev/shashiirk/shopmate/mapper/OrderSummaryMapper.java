package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.OrderSummary;
import dev.shashiirk.shopmate.dto.OrderSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link OrderSummary} and its DTO {@link OrderSummaryDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderSummaryMapper extends EntityMapper<OrderSummaryDTO, OrderSummary> {
}
