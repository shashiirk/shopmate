package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.OrderSummary;
import dev.shashiirk.shopmate.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A DTO for the {@link OrderSummary} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {

    private Long id;

    private String orderId;

    private OrderStatus status;

    private Double totalAmount;

    private UserDTO user;

    private CartDTO cart;

    private List<OrderItemDTO> orderItems;

}
