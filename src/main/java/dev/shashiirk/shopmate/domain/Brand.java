package dev.shashiirk.shopmate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * A Brand.
 */
@Getter
@Setter
@Entity
@Table(name = "brand")
public class Brand extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @SequenceGenerator(name = "common_seq", sequenceName = "common_seq")
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Brand{" +
               "id=" + id +
               '}';
    }

}
