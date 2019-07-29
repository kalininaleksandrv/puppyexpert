package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.eyeslessdev.needmypuppyapi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserRepo userRepo;


    @CrossOrigin
    @GetMapping("/messagestomod")
    public Optional<List<Feedback>> getFeedbackById() {
        //here we're return list of unmoderated feedback messages
        return feedbackService.findUnmoderatedFeedback(0);
    }

    @CrossOrigin
    @GetMapping("/getallusers")
    public List<User> findAll () {
        return userRepo.findAll();
    }


    @CrossOrigin
    @GetMapping("/user/{id}")
    public Optional<User> getUserById (@PathVariable long id) {
        return userRepo.findById(id);
    }
}
