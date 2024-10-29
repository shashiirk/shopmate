package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Brand} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    private String description;

    private Integer sortOrder;
}
