package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.eyeslessdev.needmypuppyapi.security.CommonConsts;
import com.eyeslessdev.needmypuppyapi.service.UserService;
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
        private UserService userService;

        @PostMapping("/signup")
        public ResponseEntity<String> signUp (@RequestBody User user){
            if (userService.saveNewUser(user).isPresent()) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>("Tакой email уже занят", HttpStatus.BAD_REQUEST);
        }
}
