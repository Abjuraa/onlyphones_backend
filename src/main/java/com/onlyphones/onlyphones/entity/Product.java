package com.onlyphones.onlyphones.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_product")
    String idProduct;

    @Column(name = "model")
    String model;

    @Column(name = "image")
    String image;

    @Column(name = "capacity")
    String capacity;

    @Column(name = "color")
    String color;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "discount")
    Integer discount;

    @Column(name = "has_discount")
    Boolean hasDiscount;

    @ManyToOne
    @JoinColumn(name = "id_category")
    @JsonBackReference
    Category category;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}