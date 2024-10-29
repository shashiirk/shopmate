package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the {@link Product} entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            "SELECT p " +
            "FROM Product p " +
            "WHERE " +
            "(:brandIds IS NULL OR p.brand.id IN :brandIds) " +
            "AND (:categoryIds IS NULL OR p.category.id IN :categoryIds)"
    )
    List<Product> findAllByFilter(List<Long> brandIds, List<Long> categoryIds, Sort sort);

}
