package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findAll();
    Optional<User> findById (Long id);
    Optional<User> findByEmail (String useremail);
    User findByName(String username);

    @Query(value = "SELECT * FROM public.usr INER JOIN public.user_role ON (id = user_id) WHERE user_role.roles = ?1 ORDER BY id", nativeQuery = true)
    Optional<List<User>> fetchCustomQuery(String userstatus);

}
