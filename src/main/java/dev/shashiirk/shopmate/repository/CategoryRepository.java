package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link Category} entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
