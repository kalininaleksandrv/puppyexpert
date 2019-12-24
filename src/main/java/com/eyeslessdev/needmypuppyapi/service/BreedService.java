package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequestFactory;
import com.eyeslessdev.needmypuppyapi.entity.SearchCriteriaBuilder;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BreedService {

    @Autowired
    private BreedRepo breedRepo;

    @Autowired
    private BreedFilterService breedFilterService;

    @Autowired
    private BreedRequestService breedRequestService;

    @Autowired
    private BreedRequestFactory breedRequestFactory;

    @Autowired
    private SearchCriteriaBuilder searchCriteriaBuilder;

    public BreedService(BreedRepo breedRepo,
                        BreedFilterService breedFilterService,
                        BreedRequestService breedRequestService,
                        BreedRequestFactory breedRequestFactory,
                        SearchCriteriaBuilder searchCriteriaBuilder) {
        this.breedRepo = breedRepo;
        this.breedFilterService = breedFilterService;
        this.searchCriteriaBuilder = searchCriteriaBuilder;
        this.breedRequestService = breedRequestService;
        this.breedRequestFactory = breedRequestFactory;
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

    public Map<String, List<Breed>> getFilteredListOfBreed(Map<String,String> allparam) {

        //1. передаем мапу с параметрами в breedRequestFactory, возвращаем объект BreedRequest
        //2.1. передаем BreedRequest в BreedRequestService
        //2.2. BreedRequestService сохраняет BreedRequest через BreedRequestRepo
        //3.1. Передаем BreedRequest в searchCriteriaBuilder, где он предобразовывается в List SearchCriteria
        //3.2. Там же в searchCriteriaBuilder передаем List SearchCriteria в BreedSpecificationBuilder
        //      - итерируем конструктором с with
        //3.3. BreedSpecificationBuilder в методе build создает через лист BreedSpecification лист Specification
        //4. делаем запрос в BreedRepo методом findAll передавая Specification
        //5. делаем запрос в BreedRepo получая 6 наиболее популярных пород
        //6. если запрос 4 или 5 вернулся пустым то формируем и возвращаем из метода EmptyMap
        //7. если запросы 4 и 5 не пустые, то передаем их а также BreedRequest в breedFilterService
        //  breedFilterService возвращает Map с ключами String и значениями List<Breed> т.е. несколько озаглавленных списков
        //  возвращаем эту Map из метода

        BreedRequest breedrequest = breedRequestFactory.getBreedRequest(allparam);

        breedRequestService.saveBreedRequest(breedrequest);

        Specification<Breed> mySpec = searchCriteriaBuilder.buildListOfCriteria(breedrequest);

        Optional<List<Breed>> myBreed =
                Optional.ofNullable(breedRepo.findAll(mySpec));

        Optional<List<Breed>> topRecomended =
                Optional.ofNullable(breedRepo.findTop6ByOrderByFavoriteDesc());

        if(myBreed.isPresent() && topRecomended.isPresent()){
            return breedFilterService.getProperBreeds(myBreed.get(), topRecomended.get(), breedrequest);
        } else return Collections.EMPTY_MAP;
    }

    private int increasefav(int favorite) {return ++favorite;}
}