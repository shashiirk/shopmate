package dev.shashiirk.shopmate.service;

import dev.shashiirk.shopmate.domain.Product;
import dev.shashiirk.shopmate.dto.ProductDTO;
import dev.shashiirk.shopmate.enumeration.ProductsSortOrder;

import java.util.List;

/**
 * Service interface for managing {@link Product}.
 */
public interface ProductService extends EntityService<ProductDTO> {

    /**
     * Check if a product exists by its ID.
     *
     * @param id the ID of the product
     * @return true if the product exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Find products by filter.
     *
     * @param brandIds    the brand IDs to filter by
     * @param categoryIds the category IDs to filter by
     * @param sortOrder   the sort order
     * @return the list of products
     */
    List<ProductDTO> findByFilter(List<Long> brandIds, List<Long> categoryIds, ProductsSortOrder sortOrder);

}
