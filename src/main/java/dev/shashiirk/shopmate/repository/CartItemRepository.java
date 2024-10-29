package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link CartItem} entity.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
