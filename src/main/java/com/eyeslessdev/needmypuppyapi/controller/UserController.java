package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.eyeslessdev.needmypuppyapi.security.CommonConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("users")
public class UserController {


        @Autowired
        private UserRepo userRepo;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @CrossOrigin
        @GetMapping("/admin/getallusers")
        public List<User> findAll () {
        return userRepo.findAll();
    }

        @CrossOrigin
        @GetMapping("{id}")
        public Optional<User> getUserById (@PathVariable long id) {
            return userRepo.findById(id);
        }


        @PostMapping("/signup")
        public void signUp (@RequestBody User user){
            if (!userRepo.findByEmail(user.getEmail()).isPresent()) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                if (user.getExternalid() == null) {user.setExternalid(UUID
                        .randomUUID()
                        .toString()
                        .concat(CommonConsts.ONUS_AUTH));}
                user.setRoles(Collections.singleton(Role.CREATEDUSER));
                userRepo.save(user);
            } else {
                System.out.println("can't create user cause of email is busy");
            }
        }

}
