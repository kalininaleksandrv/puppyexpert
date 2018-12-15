package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
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
    private BreedService breedService;

    @GetMapping
    @RequestMapping("breeds")
    public List<Breed> getAllBreedsOrderedByTitle (){

        return breedService.getAllBreedsOrderedByTitle();
    }

    @GetMapping
    @RequestMapping("breedsbyid")
    public List<Breed> getAllBreedsById (){

        return breedService.getAllBreedsOrderedById();
    }

}