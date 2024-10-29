package dev.shashiirk.shopmate.domain;

import dev.shashiirk.shopmate.enumeration.WishlistItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * A WishlistItem.
 */
@Getter
@Setter
@Entity
@Table(name = "wishlist_item")
public class WishlistItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @SequenceGenerator(name = "common_seq", sequenceName = "common_seq")
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WishlistItemStatus status;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistItem that = (WishlistItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WishlistItem{" +
               "id=" + id +
               '}';
    }
    
}
