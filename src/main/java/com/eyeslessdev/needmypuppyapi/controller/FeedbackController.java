package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @CrossOrigin
    @GetMapping("{id}")
    public Optional<List<Feedback>> getFeedbackById(@PathVariable long id) {
        //here we're return optional instead of object.orElseThrow(()-> new NotFoundException()) because frontend wont handle NPE correctly in this case (through interceptor)
        return feedbackService.findByDogid(id);
    }

    @PostMapping
    public ResponseEntity<List<String>> sendFeedback(@Valid @RequestBody Feedback feedback,
                                                     BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            if (feedbackService.saveFeedback(feedback)){
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        }
    }

}
