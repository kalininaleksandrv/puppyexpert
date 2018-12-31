package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    public List<Breed> findAll (){

        return breedRepo.findAll();
    }

    public List<Breed> getAllBreedsOrderedById(){
        List<Breed> rawlist = breedRepo.findAll();
        rawlist.sort(Comparator.comparing(Breed::get_id));
        return rawlist;
    }

    public List<Breed> getAllBreedsOrderedByTitle(){
        return breedRepo.findAllByOrderByTitle();
    }


    public Optional<Breed> getBreedById (long id) {

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
        return ResponseEntity.noContent().build();}
    }


    private int increasefav(int favorite) {
        return ++favorite;
    }
}