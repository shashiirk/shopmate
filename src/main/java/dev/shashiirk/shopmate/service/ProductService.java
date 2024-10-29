package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.Product;
import dev.shashiirk.shopmate.dto.ProductDTO;
import dev.shashiirk.shopmate.enumeration.ProductsSortOrder;

import java.util.List;

/**
 * Service interface for managing {@link Product}.
 */
public interface ProductService extends EntityService<ProductDTO> {

    List<ProductDTO> findByFilter(List<Long> brandIds, List<Long> categoryIds, ProductsSortOrder sortOrder);

}
