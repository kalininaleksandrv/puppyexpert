package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.exceptions.NotFoundException;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("breeds")
public class BreedController {

    @Autowired
    private BreedService breedService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Map<String, List<Breed>>> getAllBreedsById (){
            return breedService.getAllBreedsOrderedById();
    }

    @GetMapping
    @RequestMapping("bytitle")
    public List<Breed> getAllBreedsOrderedByTitle (){

        return breedService.getAllBreedsOrderedByTitle().orElseThrow(NotFoundException::new);
    }


    @CrossOrigin
    @GetMapping("{id}")
    public Breed getBreedById(@PathVariable long id) {
        return breedService.getBreedById(id).orElseThrow(NotFoundException::new);
    }


    @CrossOrigin
    @GetMapping("faved/{id}")
    public ResponseEntity<Object> faveBreed(@PathVariable long id) {
        return breedService.faveBreedById(id);
    }

    @CrossOrigin
    @GetMapping
    @RequestMapping("filtered")
    public List<Breed> getFilteredBreeds(@RequestParam Map<String,String> allparam){

        Set<Map.Entry<String, String>> entries = allparam.entrySet();

        return breedService.getFilteredListOfBreed(allparam).orElseThrow(NotFoundException::new);
    }
}