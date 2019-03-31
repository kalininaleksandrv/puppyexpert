package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.*;
import com.eyeslessdev.needmypuppyapi.exceptions.NotFoundException;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    @Autowired
    private BreedRequestRepo breedRequestRepo;

    @Autowired
    private BreedRequestParsingService breedRequestParsingService;

    @Autowired
    private BreedSelectorService breedSelectorService;

    @Autowired
    private BreedRequestFactory breedRequestFactory;

    public List<Breed> findAll() {

        return breedRepo.findAll();
    }

    public ResponseEntity<Map<String, List<Breed>>> getAllBreedsOrderedById() {

        Optional<List<Breed>> myBreed = breedRepo.findAllByOrderById();
        if(myBreed.isPresent()){
        Map <String, List<Breed>> searchingresult = new HashMap<>();
        searchingresult.put("Список всех пород", myBreed.get());
        return new ResponseEntity<>(searchingresult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<List<Breed>> getAllBreedsOrderedByTitle() {return Optional.ofNullable(breedRepo.findAllByOrderByTitle());
    }


    public Optional<Breed> getBreedById(long id) {

        return breedRepo.findById(id);
    }


    public ResponseEntity<Object> faveBreedById(long id) {

        Optional<Breed> breedOptional = getBreedById(id);

        if (!breedOptional.isPresent())
            return ResponseEntity.notFound().build();
        else {
            Breed breed = breedOptional.get();
            breed.setFavorite(increasefav(breed.getFavorite()));
            breedRepo.save(breed);
            return ResponseEntity.noContent().build();
        }
    }


    public Optional<List<Breed>> getFilteredListOfBreed(Map<String, String> allparam) {

        Map<String, Integer> brpselect = breedRequestParsingService.incomeToSelectorReadyMap(allparam);
        Map<String, Integer> brpconstraint = breedRequestParsingService.incomeToConstraintMap(allparam);
        Map<String, String> brpexterier = breedRequestParsingService.incomeToExterierMap(allparam);

        breedRequestRepo.save(breedRequestFactory.getBreedReqwest(brpselect, brpconstraint, brpexterier));

        List<SearchCriteria>  selectorList = breedSelectorService.getCriteriaListFromSelector(breedRequestParsingService.incomeToSelectorReadyMap(allparam));
        List<SearchCriteria>  exterierList = breedSelectorService.getCriteriaListFromExterier(breedRequestParsingService.incomeToExterierMap(allparam));
        selectorList.addAll(exterierList);

        BreedSpecificationBuilder bsb = new BreedSpecificationBuilder();

        for(SearchCriteria item : selectorList){
            bsb = bsb.with(item);
        }

        return Optional.ofNullable(breedRepo.findAll(bsb.build()));
    }

    private int increasefav(int favorite) {return ++favorite;}
}