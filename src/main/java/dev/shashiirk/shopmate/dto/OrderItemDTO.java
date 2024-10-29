package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link OrderItem} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;

    private OrderDTO order;

    private ProductDTO product;

    private Integer quantity;

    private Double amount;

}
