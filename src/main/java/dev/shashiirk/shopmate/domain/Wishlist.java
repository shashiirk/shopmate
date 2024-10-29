package dev.shashiirk.shopmate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * A Wishlist.
 */
@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @SequenceGenerator(name = "common_seq", sequenceName = "common_seq")
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
               "id=" + id +
               '}';
    }

}
