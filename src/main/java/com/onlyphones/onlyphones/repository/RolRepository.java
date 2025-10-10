package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, String> {
    Optional<Rol> findByRol(String rol);
}
