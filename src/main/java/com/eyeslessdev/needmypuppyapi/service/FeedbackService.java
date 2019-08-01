package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private UserRepo userRepo;

    public Optional<List<Feedback>> findByDogid (long id){

        return feedbackRepo.findTop10ByDogidOrderByCommenttimeDesc(id); // TODO: 29.07.2019 make return moderated only
    }

    public Boolean saveFeedback (Feedback feedback)  {
     try {

         DateTime nowtime = DateTime.now();
         DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
         feedback.setCommenttime(nowtime.getMillis());
         feedback.setCommenttimestr(nowtime.toString(dtf));
         feedback.setIsModerated(0);
         feedback.setUsername(getAuthenticatedPrincipalUserName());
         feedbackRepo.save(feedback);
         return true;
     } catch (Exception e) {
        e.printStackTrace();
        return false;
     }
    }

    private String getAuthenticatedPrincipalUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

           Optional<User> user =  userRepo.findByEmail(authentication.getName());

           if (user.isPresent()){
               return user.get().getName();
           } else return "anonimous";

        } else {
            return "anonimous";
        }
    }

    public Optional<List<Feedback>> findUnmoderatedFeedback (Integer ismoderated){
        return feedbackRepo.findByIsmoderated(ismoderated);
    }

    @Async
    public Boolean deleteModeratedFromDb (Map<String, String> income){

        Set<String> deletedset = income.entrySet().stream()
                .filter(m -> m.getValue()
                        .contains("DELETE"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ready to delete "+deletedset+ " time "+ Thread.currentThread().getName() + " " + System.currentTimeMillis());

        return true;
    }

    @Async
    public Boolean updateModeratedInDb (Map<String, String> income){

        Set<String> updateset = income.entrySet().stream()
                .filter(m -> m.getValue()
                        .contains("UPDATE"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ready to update "+ updateset + " time "+ Thread.currentThread().getName()+ " " + System.currentTimeMillis());

        return true;
    }

}
