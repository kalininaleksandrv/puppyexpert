package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.service.FeedbackService;
import com.eyeslessdev.needmypuppyapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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
    public ResponseEntity<List<String>> moderateMessages (@RequestBody List<Map<String, List<Integer>>> moderatedmessages){

        CompletableFuture<Boolean> resultofdeleting = feedbackService.deleteModeratedFromDb(moderatedmessages);
        CompletableFuture<Boolean> resultofupdating = feedbackService.updateModeratedInDb(moderatedmessages);

        CompletableFuture<Boolean> allWrites = resultofdeleting
                .thenCombine(resultofupdating, (res1, res2) -> res1&res2)
                .whenComplete((result, ex) -> {
                    if (ex!=null){System.out.println(ex.toString());}
                });

        if(allWrites.join()){return new ResponseEntity<>(HttpStatus.OK);}
        else {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }


    @CrossOrigin
    @GetMapping("/getallusers")
    public List<User> findAll () {return userService.findAll();}

    @CrossOrigin
    @GetMapping("/getallusersbystatus")
    public ResponseEntity<List<User>> findAllByStatus (@RequestParam String status) {

        try {
            Role role = Role.valueOf(status);
            Optional<List<User>> fetchedlist = userService.findAllCreated(role.getAuthority());
            return fetchedlist.map(users -> new ResponseEntity<>(users, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/user/{id}")
    public Optional<User> getUserById (@PathVariable long id) {
        return userService.findById(id);
    }

    @CrossOrigin
    @PostMapping("/user/{id}")
    public ResponseEntity<List<String>> updateUserById (@PathVariable Long id, @RequestBody User user ) {

        if (userService.changeStatus(id, user.getRoles())){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
}
