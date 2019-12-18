package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedTest;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRepo;
import org.hamcrest.Matcher;
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
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BreedServiceTest {

    @Mock
    private BreedRepo breedRepo;

    @InjectMocks
    private BreedService breedService;

    private static List<Breed> breedlist;

    private static Breed testbreed;

    @BeforeAll()
    static void setUp() {

        breedlist = new  ArrayList<>(Arrays.asList(
                new BreedTest((long) 1, "Dog1", "Firstdog"),
                new BreedTest((long) 2, "Dog2", "Seconddog"),
                new BreedTest((long) 3, "Dog3", "Thirddog")
        ));

        testbreed = breedlist.get(0);
    }

    @BeforeEach
    void init(){
        when(breedService.findAll()).thenReturn(breedlist);
    }

    @Test
    void findAll(){

        assertThat(testbreed).hasSameClassAs(new BreedTest());

        assertEquals(3, breedlist.size());
        assertTrue(breedlist.stream().allMatch(BreedTest.class::isInstance));
        assertThat(breedlist).hasSameClassAs(new ArrayList<>());
        assertThat(breedlist).extractingResultOf("hashCode").doesNotHaveDuplicates();
        assertThat(breedlist).extracting("title").containsExactly("Dog1","Dog2","Dog3");
        assertThat(breedlist).extracting("title").containsExactlyInAnyOrder("Dog2","Dog1","Dog3");
        assertThat(breedlist).extracting("title").doesNotContain("Dog4");
    }
}
