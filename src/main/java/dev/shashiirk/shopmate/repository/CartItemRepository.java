package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.enumeration.CartItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the {@link CartItem} entity.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find all cart items by cart ID and status.
     *
     * @param cartId The ID of the cart.
     * @param status The status of the cart item.
     * @return The list of cart items.
     */
    List<CartItem> findAllByCartIdAndStatus(Long cartId, CartItemStatus status);

}
