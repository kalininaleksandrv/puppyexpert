package com.eyeslessdev.needmypuppyapi.repositories;


import com.eyeslessdev.needmypuppyapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
