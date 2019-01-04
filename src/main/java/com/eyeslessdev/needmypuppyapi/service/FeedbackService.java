package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepo feedbackRepo;

    public Optional<List<Feedback>> findByDogid (long id){

        return feedbackRepo.findByDogid(id);
    }

    public void saveFeedback (Feedback feedback) {
        feedbackRepo.save(feedback);
    }

}
