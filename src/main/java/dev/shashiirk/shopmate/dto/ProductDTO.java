package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Product} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Double originalPrice;

    private Double sellingPrice;

    private BrandDTO brand;

    private CategoryDTO category;

    private Integer sortOrder;

}
