package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.OrderSummary;
import dev.shashiirk.shopmate.dto.OrderSummaryDTO;

/**
 * Service interface for managing {@link OrderSummary}.
 */
public interface OrderSummaryService extends EntityService<OrderSummaryDTO> {

    String generateOrderId();

}
