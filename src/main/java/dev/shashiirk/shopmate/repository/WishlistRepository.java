package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the {@link Wishlist} entity.
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * Find a wishlist by user ID.
     *
     * @param userId The ID of the user.
     * @return The wishlist.
     */
    Optional<Wishlist> findByUserId(Long userId);

}
