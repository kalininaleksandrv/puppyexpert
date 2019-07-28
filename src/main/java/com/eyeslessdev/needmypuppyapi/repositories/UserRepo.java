package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findAll();
    Optional<User> findById (Long id);
    Optional<User> findByEmail (String useremail);

    User findByName(String username);
}
