package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link Order} entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
