package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.entity.Rol;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.repository.RolRepository;
import com.onlyphones.onlyphones.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
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

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("no se pudo eliminar el usuario"));
        userRepository.delete(user);
    }
}
