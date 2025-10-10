package com.onlyphones.onlyphones.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_rol", nullable = false)
    private String idRol;

    @Column(name = "rol", nullable = false)
    private String rol;
}
