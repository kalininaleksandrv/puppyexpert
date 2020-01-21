package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
        public ResponseEntity<String> signUp (@RequestBody User user){
            if (userService.saveNewUser(user).isPresent()) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>("Извините, этот email уже занят", HttpStatus.BAD_REQUEST);
        }
}
