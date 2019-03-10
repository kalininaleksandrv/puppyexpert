package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Breed> getAllBreedsOrderedById() {
        List<Breed> rawlist = breedRepo.findAll();
        rawlist.sort(Comparator.comparing(Breed::getId));
        return rawlist;
    }

    public List<Breed> getAllBreedsOrderedByTitle() {
        return breedRepo.findAllByOrderByTitle();
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

        BreedRequest breedRequest = parserequest(allparam);

        int forhunt = Integer.parseInt(allparam.get("forhunt"));
        int forobidence = Integer.parseInt(allparam.get("forobidence"));

        return breedRepo.findQuery(forhunt, forobidence);
    }

    //helpers

    private BreedRequest parserequest(Map<String, String> allparam) {

        System.out.println(allparam);
        System.out.println(breedRequestParsingService.incomeToSelectorReadyMap(allparam));
        System.out.println(breedRequestParsingService.incomeToConstraintMap(allparam));
        System.out.println(breedRequestParsingService.incomeToExterierMap(allparam));
        System.out.println(breedSelectorService.getMapReadyToSelectFromDb(breedRequestParsingService.incomeToSelectorReadyMap(allparam)));


        return null;
    }

    private int increasefav(int favorite) {return ++favorite;}
}