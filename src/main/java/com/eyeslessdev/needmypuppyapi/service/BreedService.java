package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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

}