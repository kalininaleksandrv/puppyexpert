package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
public class BreedController {

    @Autowired
    private BreedRepo breedRepo;

    @GetMapping
    @RequestMapping("breeds")
    public List<Breed> getAllBreeds (){

        return breedRepo.findAllByOrderByTitle();
    }

    @GetMapping
    @RequestMapping("breedsbyid")
    public List<Breed> getBreedsById (){

        List<Breed> rawlist = breedRepo.findAll();
        rawlist.sort(Comparator.comparing(Breed::get_id));
        return rawlist;

    }

}