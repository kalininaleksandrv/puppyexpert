package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedSpecificationBuilder;
import com.eyeslessdev.needmypuppyapi.entity.SearchCriteria;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    @Autowired
    private BreedRequestParsingService breedRequestParsingService;

    @Autowired
    private BreedSelectorService breedSelectorService;

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

    public ResponseEntity<Map<String, List<Breed>>>  getFilteredListOfBreed(Map<String, String> allparam) {

        Optional<List<Breed>> myBreed = Optional.ofNullable(breedRepo.findAll(getSpecification(allparam)));

        if(myBreed.isPresent()){
            Map<String, List<Breed>> searchingresult = getProperBreeds(myBreed.get(), breedRequestParsingService.incomeToConstraintMap(allparam));
            return new ResponseEntity<>(searchingresult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //todo move to separate class
    private Specification getSpecification (Map<String, String> allparam) {

        List<SearchCriteria>  selectorList = breedSelectorService.getCriteriaListFromSelector(breedRequestParsingService.incomeToSelectorReadyMap(allparam));
        List<SearchCriteria>  exterierList = breedSelectorService.getCriteriaListFromExterier(breedRequestParsingService.incomeToExterierMap(allparam));
        selectorList.addAll(exterierList);

        BreedSpecificationBuilder bsb = new BreedSpecificationBuilder();

        for(SearchCriteria item : selectorList){
            bsb = bsb.with(item);
        }

        return bsb.build();

    }

    //todo move to separate class
    private Map<String, List<Breed>> getProperBreeds(List<Breed> myBreed, Map<String, Integer> brpconstraint) {

        //if constrains = 1 it means that user want to choose breed depends on this exactly param (param=1)
        // and don't care about others means it could be 0 or 1 (so it is < 2)

        Predicate <Breed> isForHunt =  (brpconstraint.get("forhunt") == 0) ?  p -> p.getForhunt()< 2 : p -> p.getForhunt() == 1;
        Predicate <Breed> isForObidience = (brpconstraint.get("forobidience") == 0) ?  p -> p.getForobidience() < 2 : p -> p.getForobidience() == 1;
        Predicate <Breed> isForAgility = (brpconstraint.get("foragility") == 0) ?  p -> p.getForagility()< 2 : p -> p.getForagility() == 1;
        Predicate <Breed> isForChild = (brpconstraint.get("forchild") == 0) ?  p -> p.getForchild()< 2 : p -> p.getForchild() == 1;
        Predicate <Breed> isForCompany =  (brpconstraint.get("forcompany") == 0) ?  p -> p.getForcompany() < 2 : p -> p.getForcompany() == 1;
        Predicate <Breed> isForRunning = (brpconstraint.get("forruning") == 0) ?  p -> p.getForrunning() < 2 : p -> p.getForrunning() == 1;
        Predicate <Breed> isForZks =  (brpconstraint.get("forzks") == 0) ?  p -> p.getForzks() < 2 : p -> p.getForzks() == 1;
        Predicate <Breed> isForGuardter = (brpconstraint.get("forguardter") == 0) ?  p -> p.getForguardterritory() < 2 : p -> p.getForguardterritory() == 1;
        Predicate <Breed> isSize =  p -> p.getSize() <= brpconstraint.get("sizeconstraintmax") && p.getSize() >= brpconstraint.get("sizeconstraintmin");

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




    private int increasefav(int favorite) {return ++favorite;}
}