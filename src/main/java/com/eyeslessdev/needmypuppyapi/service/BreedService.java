package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.SearchCriteriaBuilder;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.slf4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BreedService {

    private BreedRepo breedRepo;

    private BreedFilterService breedFilterService;

    private SearchCriteriaBuilder searchCriteriaBuilder;

    private UserService userService;

    private Logger logger;

    public BreedService(BreedRepo breedRepo,
                        BreedFilterService breedFilterService,
                        SearchCriteriaBuilder searchCriteriaBuilder,
                        UserService userService, Logger logger) {
        this.breedRepo = breedRepo;
        this.breedFilterService = breedFilterService;
        this.searchCriteriaBuilder = searchCriteriaBuilder;
        this.userService = userService;
        this.logger = logger;
    }


    public List<Breed> findAll() {
        return breedRepo.findAll();
    }

    public Map<String, List<? extends Breed>> getAllBreedsOrderedById() {

        Map<String, List<? extends Breed>> searchingresult = new HashMap<>();
        Optional<List<Breed>> myBreed = breedRepo.findAllByOrderById();

        if(myBreed.isPresent()) {
            searchingresult.put("Список всех пород", new ArrayList<>(myBreed.get()));
        } else searchingresult.put("Список всех пород", Collections.emptyList());
        logger.warn("BreedService, " +
                "getAllBreedsOrderedById() , " +
                "for some reason method returns empty collection");
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
                logger.warn("BreedService, " +
                        "faveBreedById(long id) , " +
                        "Exception: "+e);
                return HttpStatus.INTERNAL_SERVER_ERROR;}
        } else return HttpStatus.NOT_FOUND;

    }

    public Map<String, List<Breed>> getFilteredListOfBreed(Map<String,String> allparam) {

        //этот код денормализован для улучшения читаемости

        //1. передаем мапу с параметрами в breedFilterService.getBreedRequest(), возвращаем объект BreedRequest
        //2.1. Передаем BreedRequest в searchCriteriaBuilder, где он предобразовывается в List SearchCriteria
        //2.2. Там же в searchCriteriaBuilder передаем List SearchCriteria в BreedSpecificationBuilder
        //      - итерируем конструктором с with
        //3.3. BreedSpecificationBuilder в методе build создает через лист BreedSpecification лист Specification
        //4. делаем запрос в BreedRepo методом findAll передавая Specification
        //5. делаем запрос в BreedRepo получая 6 наиболее популярных пород
        //6.1. передаем BreedRequest и текущего пользователя полученного из userService.getAuthenticatedPrincipalUserName() в BreedFilterService.saveBreedRequest()
        //6.2. BreedFilterService.saveBreedRequest() сохраняет BreedRequest через BreedRequestRepo
        //7. если запрос 4 или 5 вернулся пустым то формируем и возвращаем из метода EmptyMap
        //8. если запросы 4 и 5 не пустые, то передаем их а также BreedRequest в breedFilterService.getProperBreeds()
        //  breedFilterService.getProperBreeds() возвращает Map с ключами String и значениями List<Breed> т.е. несколько озаглавленных списков
        //  возвращаем эту Map из метода

        BreedRequest breedrequest = breedFilterService.getBreedRequest(allparam);

        Specification<Breed> mySpec = searchCriteriaBuilder.buildListOfCriteria(breedrequest);

        Optional<List<Breed>> myBreed =
                Optional.ofNullable(breedRepo.findAll(mySpec));

        Optional<List<Breed>> topRecomended =
                Optional.ofNullable(breedRepo.findTop6ByOrderByFavoriteDesc());

        breedFilterService.saveBreedRequest(breedrequest, userService.getAuthenticatedPrincipalUserEmail());

        if(myBreed.isPresent() && topRecomended.isPresent())
            return breedFilterService.getProperBreeds(myBreed.get(), topRecomended.get(), breedrequest);
        else {
            logger.warn("BreedService, " +
                    "getFilteredListOfBreed(Map<String,String> allparam), " +
                    "for some reason method returns empty map");
            return Collections.emptyMap();
        }
    }

    private int increasefav(int favorite) {return ++favorite;}
}