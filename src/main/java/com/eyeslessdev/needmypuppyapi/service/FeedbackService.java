package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
import java.util.concurrent.CompletableFuture;
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
    public CompletableFuture<Boolean> deleteModeratedFromDb (Map<String, String> income){

        CompletableFuture<Boolean> allWrites = new CompletableFuture<>();

        Set<Long> deleteset = income.entrySet().stream()
                .filter(m -> m.getValue()
                        .contains("DELETE"))
                .map(Map.Entry::getKey)
                .map(s -> Long.parseLong(s))
                .collect(Collectors.toSet());

        Integer isdeleted = feedbackRepo.deleteFeedbackById(deleteset);

        System.out.println("delete "+ isdeleted.toString() + " time "+ Thread.currentThread().getName() + " " + System.currentTimeMillis());

        return CompletableFuture.completedFuture(true);
    }

    @Async
    public CompletableFuture<Boolean> updateModeratedInDb (Map<String, String> income){

        Set<Long> updateset = income.entrySet().stream()
                .filter(m -> m.getValue()
                        .contains("UPDATE"))
                .map(Map.Entry::getKey)
                .map(s -> Long.parseLong(s))
                .collect(Collectors.toSet());

        Integer isupdated = feedbackRepo.updateFeedbackById(1, updateset);

        System.out.println("update "+ isupdated.toString() + " time "+ Thread.currentThread().getName()+ " " + System.currentTimeMillis());

        return CompletableFuture.completedFuture(true);
    }

}
