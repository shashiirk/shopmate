package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Category;
import dev.shashiirk.shopmate.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
}
