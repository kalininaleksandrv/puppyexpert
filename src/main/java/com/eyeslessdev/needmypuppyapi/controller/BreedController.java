package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("breeds")
public class BreedController {

    @Autowired
    private BreedRepo breedRepo;

    @GetMapping
    public List<Map<String, String>> list (){

        List<Map<String, String>> returnedlist = new ArrayList<>();
        List<Breed> allBreeds = breedRepo.findAll();

        for (Breed allBreed : allBreeds) {
            Map<String, String> breedsmap = new HashMap<>();
            breedsmap.put("Title", allBreed.getTitle());
            breedsmap.put("Description", allBreed.getDescription());
            returnedlist.add(breedsmap);
        }
        return returnedlist;
    }
}