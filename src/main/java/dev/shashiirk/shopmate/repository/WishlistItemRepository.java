package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link WishlistItem} entity.
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
