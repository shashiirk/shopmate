package dev.shashiirk.shopmate.domain;

import dev.shashiirk.shopmate.enumeration.CartStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * A Cart.
 */
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @SequenceGenerator(name = "common_seq", sequenceName = "common_seq")
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cart{" +
               "id=" + id +
               '}';
    }
    
}
