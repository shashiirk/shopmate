package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.enumeration.CartItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link CartItem} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long id;

    private CartItemStatus status;

    private CartDTO cart;

    private ProductDTO product;

    private Integer quantity;

    private Double amount;
}
