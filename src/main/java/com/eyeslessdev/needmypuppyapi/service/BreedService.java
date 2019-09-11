package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.SearchCriteriaBuilder;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    @Autowired
    private BreedFilterService breedFilterService;

    @Autowired
    private SearchCriteriaBuilder searchCriteriaBuilder;



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

    public ResponseEntity<Map<String, List<Breed>>>  getFilteredListOfBreed(BreedRequest breedrequest) {


        Optional<List<Breed>> myBreed = Optional.ofNullable(breedRepo.findAll(searchCriteriaBuilder.buildListOfCriteria(breedrequest)));

        Optional<List<Breed>> topRecomended = Optional.ofNullable(breedRepo.findTop6ByOrderByFavoriteDesc());

        if(myBreed.isPresent() && topRecomended.isPresent()){
            Map<String, List<Breed>> searchingresult = breedFilterService.getProperBreeds(myBreed.get(), topRecomended.get(), breedrequest);
            return new ResponseEntity<>(searchingresult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    public Optional<List<Breed>> getAllBreedsOrderedByTitle() {return Optional.ofNullable(breedRepo.findAllByOrderByTitle());
    }


    public Optional<Breed> getBreedById(long id) {

        return breedRepo.findById(id);
    }


    public ResponseEntity faveBreedById(long id) {

        Optional<Breed> breedOptional = getBreedById(id);

        if (breedOptional.isPresent()){

            try {
                Breed breed = breedOptional.get();
                breed.setFavorite(increasefav(breed.getFavorite()));
                breedRepo.save(breed);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);            }
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }




    private int increasefav(int favorite) {return ++favorite;}
}