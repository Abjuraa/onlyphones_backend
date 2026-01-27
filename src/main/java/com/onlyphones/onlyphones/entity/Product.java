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

    @Column(name = "public_image_id")
    String ImagePublicId;

    @Column(name = "capacity")
    String capacity;

    @Column(name = "color")
    String color;

    @Column(name = "units_available")
    Integer unitsAvailable;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "discount")
    Integer discount;

    @Column(name = "has_discount")
    Boolean hasDiscount;

    @Column(name = "has_available")
    Boolean hasAvailable;

    @Column(name = "battery_percentage")
    Integer batteryPercentage;

    @Column(name = "grade")
    String grade;

    @Column(name = "warranty")
    Integer warranty;

    @Column(name = "physical_state")
    String physicalState;

    @Column(name = "history")
    String history;

    @Column(name = "screen")
    String screen;

    @Column(name = "processor")
    String processor;

    @Column(name = "camera")
    String camera;

    @Column(name = "security")
    String security;

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