package dev.shashiirk.shopmate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * A Product.
 */
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    @SequenceGenerator(name = "common_seq", sequenceName = "common_seq")
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "original_price", nullable = false)
    private Double originalPrice;

    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               '}';
    }

}
