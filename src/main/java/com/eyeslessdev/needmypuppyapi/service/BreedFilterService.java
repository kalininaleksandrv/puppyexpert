package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequestFactory;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRequestRepo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class BreedFilterService {

    private BreedRequestRepo breedRequestRepo;

    private BreedRequestFactory breedRequestFactory;

    BreedFilterService(BreedRequestRepo breedRequestRepo, BreedRequestFactory breedRequestFactory) {
        this.breedRequestRepo = breedRequestRepo;
        this.breedRequestFactory = breedRequestFactory;
    }

    public BreedRequest getBreedRequest(Map<String, String> allparam) {
        return breedRequestFactory.getBreedRequest(allparam);
    }

    Map<String, List<Breed>> getProperBreeds(List<Breed> myBreed, List<Breed> topRecomended, BreedRequest breedRequest) {

        Predicate<Breed> isForHunt =  (breedRequest.getForhunt() == 0) ? p -> p.getForhunt()< 2 : p -> p.getForhunt() == 1;
        Predicate <Breed> isForObidience = (breedRequest.getForobidience() == 0) ?  p -> p.getForobidience() < 2 : p -> p.getForobidience() == 1;
        Predicate <Breed> isForAgility = (breedRequest.getForagility() == 0) ?  p -> p.getForagility()< 2 : p -> p.getForagility() == 1;
        Predicate <Breed> isForChild = (breedRequest.getForchild() == 0) ?  p -> p.getForchild()< 2 : p -> p.getForchild() == 1;
        Predicate <Breed> isForCompany =  (breedRequest.getForcompany() == 0) ?  p -> p.getForcompany() < 2 : p -> p.getForcompany() == 1;
        Predicate <Breed> isForRunning = (breedRequest.getForruning() == 0) ?  p -> p.getForrunning() < 2 : p -> p.getForrunning() == 1;
        Predicate <Breed> isForZks =  (breedRequest.getForzks() == 0) ?  p -> p.getForzks() < 2 : p -> p.getForzks() == 1;
        Predicate <Breed> isForGuardter = (breedRequest.getForguardter() == 0) ?  p -> p.getForguardterritory() < 2 : p -> p.getForguardterritory() == 1;
        Predicate <Breed> isSize =  p -> p.getSize() <= breedRequest.getSizeconstraintmax() && p.getSize() >= breedRequest.getSizeconstraintmin();

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
                .filter(isSize)
                .forEach(p -> {
                    if (!outcomelist.contains(p)) {
                        forCompanyList.add(p);
                    }
                });

        String secondlistatatus = "Вам также могут подойти ";
        String topliststatus = "TOP популярных ";

        Map <String, List<Breed>> searchingresult = new HashMap<>();
        if (outcomelist.size()>0) {searchingresult.put("Самые подходящие породы ", outcomelist);}
            else {secondlistatatus = "Полного соотетсвия для Вашего запроса не найдено, но вот какие породы оказались ближе всего";}

        if (forCompanyList.size()>0) {searchingresult.put(secondlistatatus, forCompanyList);}
            else{topliststatus = "К сожалению, подходящих пород не найдено, но посмотрите, кто больше всего понравился другим посетителям";}

        if (topRecomended.size()>0) searchingresult.put(topliststatus, topRecomended);

        return searchingresult;

    }

    @Async("threadPoolTaskExecutor")
    public void saveBreedRequest(BreedRequest request, String authenticatedPrincipalUserName){

        DateTime nowtime = DateTime.now();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("Created by: ");
        sb.append(authenticatedPrincipalUserName);
        sb.append(", at: ");
        sb.append(nowtime.toString(dtf));

        request.setRequestcreatetime(nowtime.getMillis());
        request.setCreator(sb.toString());

        breedRequestRepo.save(request);
    }
}
