package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link Brand} entity.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
