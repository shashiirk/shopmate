package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link Wishlist} entity.
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
