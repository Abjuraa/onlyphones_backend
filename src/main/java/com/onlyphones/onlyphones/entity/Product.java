package com.onlyphones.onlyphones.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;

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

    @ManyToOne
    @JoinColumn(name = "id_category")
    Category category;

    @Column(name = "model")
    String model;

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

}