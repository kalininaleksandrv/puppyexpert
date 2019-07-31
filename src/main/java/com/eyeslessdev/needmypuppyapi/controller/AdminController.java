package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.service.FeedbackService;
import com.eyeslessdev.needmypuppyapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/messagestomod")
    public Optional<List<Feedback>> getFeedbackById() {
        //here we're return list of unmoderated feedback messages
        return feedbackService.findUnmoderatedFeedback(0);
    }

    @CrossOrigin
    @PostMapping("/messagestomod")
    public ResponseEntity<List<String>> moderateMessages (@RequestBody Map<String, String> moderatedmessages){

        if (feedbackService.saveModerationResultToFeedbackDb(moderatedmessages)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("/getallusers")
    public List<User> findAll () {return userService.findAll();}


    @CrossOrigin
    @GetMapping("/user/{id}")
    public Optional<User> getUserById (@PathVariable long id) {
        return userService.findById(id);
    }

    @CrossOrigin
    @PostMapping("/user/{id}")
    public ResponseEntity<List<String>> updateUserById (@PathVariable Long id, @RequestBody User user ) {
        // TODO: 29.07.2019 could I replace user by Map<String, String[]>??? 

        if (userService.changeStatus(id, user.getRoles())){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }


    // deleting multiply messages DELETE from public.feedback WHERE id in (10, 17, 18);
    // make multiply messages moderated UPDATE public.feedback SET ismoderated = 1 WHERE id = 10 OR id = 11;
    //change user status by id
}
