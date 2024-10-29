package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.Order;
import dev.shashiirk.shopmate.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Order} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private OrderStatus status;

    private Double totalAmount;

    private UserDTO user;

    private CartDTO cart;
}
