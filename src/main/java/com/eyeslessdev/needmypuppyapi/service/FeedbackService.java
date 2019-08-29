package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    public List findByDogid (long id){

        Optional<List<Feedback>> mylist = feedbackRepo.findTop10ByDogidOrderByCommenttimeDesc(id);

        return mylist.map(feedbacks -> feedbacks.stream()
                .filter(list -> list.getIsModerated() == 1)
                .collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }

    public Boolean saveFeedback (Feedback feedback)  {

        Collection<? extends GrantedAuthority> currentPrincipalName = userService.getAuthenticatedPrincipalUserRole();

        try {
             if (currentPrincipalName.contains(Role.USER) || currentPrincipalName.contains(Role.ADMIN))
                 feedback.setIsModerated(1);
             else feedback.setIsModerated(0);

             DateTime nowtime = DateTime.now();
             DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
             feedback.setCommenttime(nowtime.getMillis());
             feedback.setCommenttimestr(nowtime.toString(dtf));
             feedback.setUsername(userService.getAuthenticatedPrincipalUserName());
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

    @Async
    public CompletableFuture<Boolean> deleteModeratedFromDb (Map<String, String> income){

        CompletableFuture<Boolean> allWrites = new CompletableFuture<>();

        Set<Long> deleteset = income.entrySet().stream()
                .filter(m -> m.getValue()
                        .contains("DELETE"))
                .map(Map.Entry::getKey)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        if(!deleteset.isEmpty()) {
            feedbackRepo.deleteFeedbackById(deleteset);
        }
        return CompletableFuture.completedFuture(true);
    }

    @Async
    public CompletableFuture<Boolean> updateModeratedInDb (Map<String, String> income){

        Set<Long> updateset = income.entrySet().stream()
                .filter(m -> m.getValue()
                        .contains("UPDATE"))
                .map(Map.Entry::getKey)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        if(!updateset.isEmpty()) {
            feedbackRepo.updateFeedbackById(1, updateset);
        }

        return CompletableFuture.completedFuture(true);
    }

}
