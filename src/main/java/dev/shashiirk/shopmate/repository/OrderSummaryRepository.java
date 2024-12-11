package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link OrderSummary} entity.
 */
@Repository
public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long> {

    boolean existsByOrderId(String orderId);

}
