package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;


@RestController
@CrossOrigin
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepo feedbackRepo;


    @PostMapping
    public void sendFeedback(@RequestBody Feedback feedback,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException("Feedback has errors!");
        }
             feedbackRepo.save(feedback);
    }

}
