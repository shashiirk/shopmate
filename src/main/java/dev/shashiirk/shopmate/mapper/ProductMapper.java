package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Product;
import dev.shashiirk.shopmate.dto.ProductDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
}
