package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.eyeslessdev.needmypuppyapi.security.CommonConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("users")
public class UserController {


        @Autowired
        private UserRepo userRepo;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @PostMapping("/signup")
        public ResponseEntity<String> signUp (@RequestBody User user){
            if (!userRepo.findByEmail(user.getEmail()).isPresent()) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                if (user.getExternalid() == null) {user.setExternalid(UUID
                        .randomUUID()
                        .toString()
                        .concat(CommonConsts.ONUS_AUTH));}
                user.setRoles(Collections.singleton(Role.CREATEDUSER));
                user.setRegistrationtime(System.currentTimeMillis());
                user.setLastvisit(System.currentTimeMillis());
                userRepo.save(user);
                return new ResponseEntity<String>(HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Tакой email уже занят", HttpStatus.BAD_REQUEST);
            }
        }

}
