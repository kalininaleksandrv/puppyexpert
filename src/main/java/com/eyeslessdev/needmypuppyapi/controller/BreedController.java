package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequestFactory;
import com.eyeslessdev.needmypuppyapi.service.BreedRequestService;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("breeds")
public class BreedController {

    @Autowired
    private BreedService breedService;

    @Autowired
    private BreedRequestService breedRequestService;


    @Autowired
    private BreedRequestFactory breedRequestFactory;

    @CrossOrigin
    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<? extends Breed>>> getAllBreedsById (){
            return breedService.getAllBreedsOrderedById();
    }

    @GetMapping
    @RequestMapping("bytitle")
    public ResponseEntity<List<Breed>> getAllBreedsOrderedByTitle (){

        return breedService.getAllBreedsOrderedByTitle()
                .map(breeds -> new ResponseEntity<>(breeds, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }


    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<Breed> getBreedById(@PathVariable long id) {
        return breedService.getBreedById(id)
                .map(breed -> new ResponseEntity<>(breed, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @CrossOrigin
    @GetMapping("faved/{id}")
    public ResponseEntity faveBreed(@PathVariable long id) {
        return breedService.faveBreedById(id);
    }

    @CrossOrigin
    @GetMapping
    @RequestMapping("filtered")
    public ResponseEntity<Map<String, List<Breed>>> getFilteredBreeds(@RequestParam Map<String,String> allparam){

        BreedRequest request = breedRequestFactory.getBreedRequest(allparam);

        breedRequestService.saveBreedRequest(request);

        return breedService.getFilteredListOfBreed(request);
    }
}