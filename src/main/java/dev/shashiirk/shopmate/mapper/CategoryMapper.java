package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Category;
import dev.shashiirk.shopmate.dto.CategoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
}
