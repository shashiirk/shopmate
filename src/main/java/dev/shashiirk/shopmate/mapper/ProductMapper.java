package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Product;
import dev.shashiirk.shopmate.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
}
