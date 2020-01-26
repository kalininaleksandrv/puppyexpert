package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private FeedbackRepo feedbackRepo;

    private UserRepo userRepo;

    private UserService userService;

    private Logger logger;

    public FeedbackService(FeedbackRepo feedbackRepo, UserRepo userRepo, UserService userService, Logger logger) {
        this.feedbackRepo = feedbackRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.logger = logger;
    }

    public List<Feedback> findByDogId(long id){

        List<Feedback> changedList = feedbackRepo.findTop10ByDogidOrderByCommenttimeDesc(id);

        return changedList.stream()
                .filter(list -> list.isModerated() == 1)
                .peek(item -> item.setEmail("access not allowed"))
                .collect(Collectors.toList());
    }

    public Boolean saveFeedback (Feedback feedback)  {

        Collection<? extends GrantedAuthority> currentPrincipalUserRole = userService.getAuthenticatedPrincipalUserRole();

        try {
            if (currentPrincipalUserRole.contains(Role.USER) || currentPrincipalUserRole.contains(Role.ADMIN))
                 feedback.setIsModerated(1);
            else feedback.setIsModerated(0);

            DateTime nowtime = DateTime.now();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
            feedback.setCommenttime(nowtime.getMillis());
            feedback.setCommenttimestr(nowtime.toString(dtf));

            String userEmail = userService.getAuthenticatedPrincipalUserEmail();

            if(!userEmail.equals("anonimous")){
                Optional<User> user  = userRepo.findByEmail(userEmail);
                user.ifPresent(value -> feedback.setUsername(value.getName()));
                feedback.setEmail(userEmail);
            } else {
                feedback.setUsername("anonimous");
                feedback.setEmail("anonimous");
            }

            feedbackRepo.save(feedback);
            return true;
     } catch (Exception e) {
            logger.warn("FeedbackService, " +
                    "saveFeedback (Feedback feedback) , " +
                    "Exception: "+e);
        return false;
     }
    }

    public List<Feedback> findUnmoderatedFeedback (Integer ismoderated){
        return feedbackRepo.findByIsmoderated(ismoderated);
    }

    @Async
    public CompletableFuture<Boolean> deleteModeratedFromDb (Map<String, List<Integer>> income){

        Set<Long> deleteset = income.entrySet().stream()
                .filter(item -> item.getKey().equalsIgnoreCase("DELETE"))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(Long::valueOf)
                .collect(Collectors.toSet());


        if(!deleteset.isEmpty()) {
            feedbackRepo.deleteFeedbackById(deleteset);
        }
        return CompletableFuture.completedFuture(true);
    }

    @Async
    public CompletableFuture<Boolean> updateModeratedInDb (Map<String, List<Integer>> income){

        Set<Long> updateset = income.entrySet().stream()
                .filter(item -> item.getKey().equalsIgnoreCase("UPDATE"))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(Long::valueOf)
                .collect(Collectors.toSet());

        if(!updateset.isEmpty()) {
            feedbackRepo.updateFeedbackById(1, updateset);
        }

        return CompletableFuture.completedFuture(true);
    }

}
