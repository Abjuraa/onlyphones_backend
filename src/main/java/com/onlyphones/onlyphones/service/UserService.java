package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.entity.Rol;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.repository.RolRepository;
import com.onlyphones.onlyphones.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User newUser) {

        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
        Rol defaultRol = rolRepository.findByRol("Client").orElseThrow(() -> new RuntimeException("El rol no se encuentra disponible"));

        if (existingUser.isPresent()) {
            throw new RuntimeException("Ya hay un usuario registrado con el correo ingresado");
        }
        newUser.setUserRol(defaultRol);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User updateUser(String id, User newData) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(newData.getEmail());
                    user.setName(newData.getName());
                    user.setPassword(newData.getPassword());
                    return user;
                })
                .map(userRepository::save)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar el usuario"));
    }

    public Boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
