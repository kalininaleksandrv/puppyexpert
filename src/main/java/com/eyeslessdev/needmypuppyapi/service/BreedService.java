package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequestFactory;
import com.eyeslessdev.needmypuppyapi.entity.BreedSpecificationBuilder;
import com.eyeslessdev.needmypuppyapi.entity.SearchCriteria;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRequestRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    @Autowired
    private BreedRequestParsingService breedRequestParsingService;

    @Autowired
    private BreedSelectorService breedSelectorService;

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

    public ResponseEntity<Map<String, List<Breed>>>  getFilteredListOfBreed(Map<String, String> allparam) {

        Optional<List<Breed>> myBreed = Optional.ofNullable(breedRepo.findAll(getSpecification(allparam)));

        if(myBreed.isPresent()){
            Map<String, List<Breed>> searchingresult = getProperBreeds(myBreed.get(), breedRequestParsingService.incomeToConstraintMap(allparam));
            return new ResponseEntity<>(searchingresult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Specification getSpecification (Map<String, String> allparam) {

        List<SearchCriteria>  selectorList = breedSelectorService.getCriteriaListFromSelector(breedRequestParsingService.incomeToSelectorReadyMap(allparam));
        List<SearchCriteria>  exterierList = breedSelectorService.getCriteriaListFromExterier(breedRequestParsingService.incomeToExterierMap(allparam));
        selectorList.addAll(exterierList);

        BreedSpecificationBuilder bsb = new BreedSpecificationBuilder();

        for(SearchCriteria item : selectorList){
            bsb = bsb.with(item);
        }

        return bsb.build();

    }

    private Map<String, List<Breed>> getProperBreeds(@NotNull List<Breed> myBreed, Map<String, Integer> brpconstraint) {

        System.out.println(brpconstraint);

        List<Breed> outcomelist = myBreed.stream()
                //here we add filter depend on brpconstraint
                .filter(breed -> breed.getSize()<3)
                .peek(breed-> System.out.println(breed.getTitle()))
                .collect(Collectors.toList());

        Map <String, List<Breed>> searchingresult = new HashMap<>();
        searchingresult.put("Самые подходящие породы", outcomelist);
        return searchingresult;
    }



    public Optional<List<Breed>> getAllBreedsOrderedByTitle() {return Optional.ofNullable(breedRepo.findAllByOrderByTitle());
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




    private int increasefav(int favorite) {return ++favorite;}
}