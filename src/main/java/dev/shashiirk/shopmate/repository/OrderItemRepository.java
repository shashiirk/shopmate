package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link OrderItem} entity.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
