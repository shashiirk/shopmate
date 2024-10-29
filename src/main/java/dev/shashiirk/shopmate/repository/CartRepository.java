package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link Cart} entity.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
