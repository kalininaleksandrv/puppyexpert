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

        int forhunt = Integer.parseInt(allparam.get("hunt"));
        int forobidence = Integer.parseInt(allparam.get("obidence"));

        return breedRepo.findQuery(forhunt, forobidence);
    }

    //helpers

    private BreedRequest parserequest(Map<String, String> allparam) {

        BreedRequest breedRequest = new BreedRequest();
        breedRequest.fillouterparams(allparam);


        System.out.println(breedRequest.toString());
        return breedRequest;
    }

    private int increasefav(int favorite) {return ++favorite;}
}