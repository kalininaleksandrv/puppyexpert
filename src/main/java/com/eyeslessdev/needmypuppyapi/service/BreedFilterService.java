package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class BreedFilterService {

    Map<String, List<Breed>> getProperBreeds(List<Breed> myBreed, BreedRequest breedRequest) {

        Predicate<Breed> isForHunt =  (breedRequest.getForhunt() == 0) ? p -> p.getForhunt()< 2 : p -> p.getForhunt() == 1;
        Predicate <Breed> isForObidience = (breedRequest.getForobidience() == 0) ?  p -> p.getForobidience() < 2 : p -> p.getForobidience() == 1;
        Predicate <Breed> isForAgility = (breedRequest.getForagility() == 0) ?  p -> p.getForagility()< 2 : p -> p.getForagility() == 1;
        Predicate <Breed> isForChild = (breedRequest.getForchild() == 0) ?  p -> p.getForchild()< 2 : p -> p.getForchild() == 1;
        Predicate <Breed> isForCompany =  (breedRequest.getForcompany() == 0) ?  p -> p.getForcompany() < 2 : p -> p.getForcompany() == 1;
        Predicate <Breed> isForRunning = (breedRequest.getForruning() == 0) ?  p -> p.getForrunning() < 2 : p -> p.getForrunning() == 1;
        Predicate <Breed> isForZks =  (breedRequest.getForzks() == 0) ?  p -> p.getForzks() < 2 : p -> p.getForzks() == 1;
        Predicate <Breed> isForGuardter = (breedRequest.getForguardter() == 0) ?  p -> p.getForguardterritory() < 2 : p -> p.getForguardterritory() == 1;
        Predicate <Breed> isSize =  p -> p.getSize() <= breedRequest.getSizeconstraintmax() && p.getSize() >= breedRequest.getSizeconstraintmin();

        //todo improve this logic
        Predicate <Breed> isForCompanyExtended =  (p -> p.getForcompany() == 1 && p.getSize()<4 && p.getObidience()>3);


        List<Breed> outcomelist = myBreed.stream()
                .filter(isSize
                        .and(isForHunt
                                .and(isForObidience
                                        .and(isForAgility
                                                .and(isForChild
                                                        .and(isForCompany
                                                                .and(isForRunning
                                                                        .and(isForZks
                                                                                .and(isForGuardter)))))))))
                .collect(Collectors.toList());

        List<Breed> forCompanyList = new ArrayList<>();

        myBreed.stream()
                .filter(isSize
                        .and(isForCompanyExtended))
                .forEach(p -> {
                    if (!outcomelist.contains(p)) {
                        forCompanyList.add(p);
                    }
                });

        Map <String, List<Breed>> searchingresult = new HashMap<>();
        searchingresult.put("Самые подходящие породы ", outcomelist);
        searchingresult.put("Рекомендуем дополнительно ", forCompanyList);

        return searchingresult;

    }
}
