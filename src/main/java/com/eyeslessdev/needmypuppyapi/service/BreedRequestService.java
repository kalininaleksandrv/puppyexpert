package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BreedRequestService {

    @Autowired
    private BreedRequestRepo breedRequestRepo;



    @Async("threadPoolTaskExecutor")
    public void saveBreedRequest (BreedRequest request){

         breedRequestRepo.save(request);

    }
}
