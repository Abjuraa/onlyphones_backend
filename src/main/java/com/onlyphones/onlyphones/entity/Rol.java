package com.onlyphones.onlyphones.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_rol", nullable = false)
    String idRol;

    @Column(name = "rol", nullable = false)
    String rol;
}
