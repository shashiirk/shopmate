package dev.shashiirk.shopmate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartActionDTO {

    private Long productId;

    private Integer quantity = 1;

    private Double amount;

}
