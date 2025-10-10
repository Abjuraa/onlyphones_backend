package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
