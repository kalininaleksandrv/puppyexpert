package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private
    FeedbackRepo feedbackRepo;

    public Optional<List<Feedback>> findByDogid (long id){

        return feedbackRepo.findTop10ByDogidOrderByCommenttimeDesc(id);
    }

    public Boolean saveFeedback (Feedback feedback)  {
     try {
         DateTime nowtime = DateTime.now();
         DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
         feedback.setCommenttime(nowtime.getMillis());
         feedback.setCommenttimestr(nowtime.toString(dtf));
         feedback.setIsModerated(0);
         feedbackRepo.save(feedback);
         return true;
     } catch (Exception e) {
        e.printStackTrace();
        return false;
     }
    }

    public Optional<List<Feedback>> findUnmoderatedFeedback (Integer ismoderated){
        return feedbackRepo.findByIsmoderated(ismoderated);
    }

}
