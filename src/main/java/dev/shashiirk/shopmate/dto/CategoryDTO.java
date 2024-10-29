package dev.shashiirk.shopmate.dto;

import dev.shashiirk.shopmate.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link Category} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Integer sortOrder;
}
