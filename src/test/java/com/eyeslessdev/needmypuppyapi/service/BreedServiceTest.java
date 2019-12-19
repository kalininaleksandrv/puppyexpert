package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedTest;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BreedServiceTest {

    @Mock
    private BreedRepo breedRepo;

    @InjectMocks
    private BreedService breedService;

    private static List<Breed> breedlist;

    @BeforeAll()
    static void setUp() {

        breedlist = new  ArrayList<>(Arrays.asList(
                new BreedTest((long) 1, "Dog3", "Firstdog"),
                new BreedTest((long) 2, "Dog2", "Seconddog"),
                new BreedTest((long) 3, "Dog1", "Thirddog")
        ));

    }

    @BeforeEach
    void init(){
        when(breedRepo.findAll()).thenReturn(breedlist);
        when(breedRepo.findAllByOrderById()).thenReturn(Optional.of(breedlist));
        when(breedRepo.findAllByOrderByTitle()).thenReturn(breedlist
                .stream()
                .sorted(Comparator.comparing(Breed::getTitle))
                .collect(Collectors.toList()));
    }

    @Test
    void testFindAll() {
        List<Breed> mybreedlist = breedService.findAll();

        assertEquals(3, mybreedlist.size());
        assertTrue(mybreedlist.stream().allMatch(BreedTest.class::isInstance));
        assertThat(mybreedlist).hasSameClassAs(new ArrayList<>());
        assertThat(mybreedlist).extractingResultOf("hashCode").doesNotHaveDuplicates();
        assertThat(mybreedlist).extracting("title").containsExactlyInAnyOrder("Dog2","Dog1","Dog3");
        assertThat(mybreedlist).extracting("title").doesNotContain("Dog4");
    }

    @Test
    void testGetAllBreedsOrderedById() {

        Map<String, List<? extends Breed>> searchingresult = breedService.getAllBreedsOrderedById();
        List<Long> listofid = searchingresult.get("Список всех пород").stream().map(Breed::getId).collect(Collectors.toList());

        assertEquals(1, searchingresult.size());
        assertTrue(searchingresult.containsKey("Список всех пород"));
        assertNotNull(searchingresult.get("Список всех пород"));
        assertTrue(searchingresult.get("Список всех пород").containsAll(breedlist));
        assertThat(listofid).containsOnly(1L,2L,3L); //check order!

    }

    @Test
    void getAllBreedsOrderedByTitle() {
        Optional<List<Breed>> mybreedlist = breedService.getAllBreedsOrderedByTitle();
        assertEquals(3, mybreedlist.orElse(Collections.EMPTY_LIST).size());
        assertThat(mybreedlist.get()).extracting("title").containsExactly("Dog1","Dog2","Dog3");
    }

    @Test
    void getBreedById() {
    }

    @Test
    void faveBreedById() {
    }

    @Test
    void getFilteredListOfBreed() {
    }
}
