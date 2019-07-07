package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("users")
public class UserController {


        @Autowired
        private UserRepo userRepo;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @CrossOrigin
        @GetMapping("{id}")
        public Optional<User> getUserById (@PathVariable long id) {
            return userRepo.findById(id);
        }


        @PostMapping("/signup")
        public void signUp (@RequestBody User user){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.CREATEDUSER));
            userRepo.save(user);
        }

}
