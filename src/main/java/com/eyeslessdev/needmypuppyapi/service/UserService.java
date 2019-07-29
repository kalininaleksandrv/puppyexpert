package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Boolean changeStatus(Long id, Set<Role> roles) {

        for (Role s:roles){
            System.out.println("new role is "+s.toString());
        }

        try {
            Optional<User> updateduser = userRepo.findById(id);
            if (updateduser.isPresent()){
                System.out.println("currentroles "+updateduser.get().getRoles());
                return true;
            } else {return false;}
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findAll() {return userRepo.findAll();}

    public Optional<User> findById(long id) {return userRepo.findById(id);}
}
