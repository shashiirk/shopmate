package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.enumeration.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the {@link Cart} entity.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Find all carts by user id and status.
     *
     * @param userId the user id
     * @param status the status
     * @return the list of carts
     */
    List<Cart> findByUserIdAndStatus(Long userId, CartStatus status);

}
