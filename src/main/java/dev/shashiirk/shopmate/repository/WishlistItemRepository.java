package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.WishlistItem;
import dev.shashiirk.shopmate.enumeration.WishlistItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the {@link WishlistItem} entity.
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    /**
     * Find all wishlist items by wishlist ID.
     *
     * @param wishlistId The ID of the wishlist.
     * @param status     The status of the wishlist item.
     * @return The list of wishlist items.
     */
    List<WishlistItem> findAllByWishlistIdAndStatus(Long wishlistId, WishlistItemStatus status);

}
