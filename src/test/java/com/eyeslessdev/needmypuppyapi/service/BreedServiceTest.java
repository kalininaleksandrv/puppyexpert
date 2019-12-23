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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

    @Test
    void testFindAll() {

        List<Breed> mybreedlist = breedService.findAll();

        assertEquals(3, mybreedlist.size());
        assertTrue(mybreedlist.stream().allMatch(BreedTest.class::isInstance));
        assertThat(mybreedlist).hasSameClassAs(new ArrayList<>());
        assertThat(mybreedlist).extractingResultOf("hashCode").doesNotHaveDuplicates();
        assertThat(mybreedlist).extracting("title").containsExactlyInAnyOrder("Dog2","Dog1","Dog3");
        assertThat(mybreedlist).extracting("title").doesNotContain("Dog4");
        Mockito.verify(breedRepo, Mockito.times(1)).findAll();
    }

    @Test
    void testGetAllBreedsOrderedById() {

        when(breedRepo.findAll()).thenReturn(breedlist);

        when(breedRepo.findAllByOrderById()).thenReturn(Optional.of(breedlist));

        Map<String, List<? extends Breed>> searchingresult = breedService.getAllBreedsOrderedById();
        List<Long> listofid = searchingresult.get("Список всех пород").stream().map(Breed::getId).collect(Collectors.toList());

        assertEquals(1, searchingresult.size());
        assertTrue(searchingresult.containsKey("Список всех пород"));
        assertNotNull(searchingresult.get("Список всех пород"));
        assertTrue(searchingresult.get("Список всех пород").containsAll(breedlist));
        assertThat(listofid).containsExactly(1L,2L,3L);

        Mockito.verify(breedRepo, Mockito.times(1)).findAllByOrderById();
    }

    @Test
    void testGetAllBreedsOrderedByIdDbCrash() {

        when(breedRepo.findAllByOrderById()).thenReturn(Optional.of(Collections.EMPTY_LIST));

        Map<String, List<? extends Breed>> searchingresult = breedService.getAllBreedsOrderedById();

        assertEquals(1, searchingresult.size());
        assertTrue(searchingresult.get("Список всех пород").isEmpty());

        Mockito.verify(breedRepo, Mockito.times(1)).findAllByOrderById();
    }

    @Test
    void getAllBreedsOrderedByTitle() {

        when(breedRepo.findAllByOrderByTitle()).thenReturn(breedlist
                .stream()
                .sorted(Comparator.comparing(Breed::getTitle))
                .collect(Collectors.toList()));

        Optional<List<Breed>> mybreedlist = breedService.getAllBreedsOrderedByTitle();

        assertEquals(3, mybreedlist.get().size());
        assertThat(mybreedlist.get()).extracting("title").containsExactly("Dog1","Dog2","Dog3");

        Mockito.verify(breedRepo, Mockito.times(1)).findAllByOrderByTitle();
    }

    @Test
    void getBreedById() {

        long arg = 1L;
        int argint = (int) arg;

        when(breedRepo.findById(arg)).thenReturn(Optional.of(breedlist.get(argint)));

        Optional<Breed> testingBreed = breedService.getBreedById(arg);

        assertNotNull(testingBreed);
        assertThat(testingBreed.get()).hasSameClassAs(breedlist.get(argint));
        assertEquals(testingBreed.get().getId(), breedlist.get(argint).getId());
    }

    @Test
    void getBreedByIdNotFound() {

        when(breedRepo.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Breed> testingBreed = breedService.getBreedById(999);

        assertEquals(testingBreed, Optional.empty());
    }

    @Test
    void faveBreedById() {
    }

    @Test
    void getFilteredListOfBreed() {
    }
}
