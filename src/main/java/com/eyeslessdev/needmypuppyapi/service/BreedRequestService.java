package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.BreedRequestFactory;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BreedRequestService {

    @Autowired
    private BreedRequestRepo breedRequestRepo;

    @Autowired
    private BreedRequestParsingService breedRequestParsingService;

    @Autowired
    private BreedRequestFactory breedRequestFactory;

    public void saveBreedRequest (Map<String, String> allparam){

         breedRequestRepo.save(breedRequestFactory.getBreedReqwest(breedRequestParsingService.incomeToSelectorReadyMap(allparam),
                        breedRequestParsingService.incomeToConstraintMap(allparam),
                        breedRequestParsingService.incomeToExterierMap(allparam)));

    }
}
