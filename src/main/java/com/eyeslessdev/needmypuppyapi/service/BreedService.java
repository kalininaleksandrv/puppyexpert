package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.SearchCriteriaBuilder;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    @Autowired
    private BreedFilterService breedFilterService;

    @Autowired
    private SearchCriteriaBuilder searchCriteriaBuilder;
    public BreedService(BreedRepo breedRepo,
                        BreedFilterService breedFilterService,
                        SearchCriteriaBuilder searchCriteriaBuilder) {
        this.breedRepo = breedRepo;
        this.breedFilterService = breedFilterService;
        this.searchCriteriaBuilder = searchCriteriaBuilder;
    }


    public List<Breed> findAll() {
        return breedRepo.findAll();
    }

    public Map<String, List<? extends Breed>> getAllBreedsOrderedById() {

        Map<String, List<? extends Breed>> searchingresult = new HashMap<>();
        Optional<List<Breed>> myBreed = breedRepo.findAllByOrderById();

        if(myBreed.isPresent()) {
            searchingresult.put("Список всех пород", new ArrayList<>(myBreed.get()));
        } else searchingresult.put("Список всех пород", Collections.EMPTY_LIST);
            return searchingresult;
    }

    public Optional<List<Breed>> getAllBreedsOrderedByTitle() {
        return Optional.ofNullable(breedRepo.findAllByOrderByTitle());
    }


    public Optional<Breed> getBreedById(long id) {
        return breedRepo.findById(id);
    }


    public HttpStatus faveBreedById(long id) {

        Optional<Breed> breedOptional = getBreedById(id);

        if (breedOptional.isPresent()){

            try {
                Breed breed = breedOptional.get();
                breed.setFavorite(increasefav(breed.getFavorite()));
                breedRepo.save(breed);
                return HttpStatus.OK;
            } catch (Exception e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;}
        } else return HttpStatus.NOT_FOUND;

    }

    public ResponseEntity<Map<String, List<Breed>>>  getFilteredListOfBreed(BreedRequest breedrequest) {

        Optional<List<Breed>> myBreed = Optional.ofNullable(breedRepo.findAll(searchCriteriaBuilder.buildListOfCriteria(breedrequest)));
        Optional<List<Breed>> topRecomended = Optional.ofNullable(breedRepo.findTop6ByOrderByFavoriteDesc());

        if(myBreed.isPresent() && topRecomended.isPresent()){
            Map<String, List<Breed>> searchingresult = breedFilterService.getProperBreeds(myBreed.get(), topRecomended.get(), breedrequest);
            return new ResponseEntity<>(searchingresult, OK);
        } else {
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    private int increasefav(int favorite) {return ++favorite;}
}