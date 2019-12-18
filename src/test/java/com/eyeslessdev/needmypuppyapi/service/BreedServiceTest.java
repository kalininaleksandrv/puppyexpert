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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
                new BreedTest((long) 1, "Dog1", "Firstdog"),
                new BreedTest((long) 2, "Dog2", "Seconddog"),
                new BreedTest((long) 3, "Dog3", "Thirddog")
        ));
    }

    @BeforeEach
    void init(){
        when(breedService.findAll()).thenReturn(breedlist);
    }

    @Test
    void findAll(){
        assertEquals(3, breedlist.size());
        assertThat(breedlist).extracting("title").contains("Dog1","Dog2","Dog3");
    }

    

}
