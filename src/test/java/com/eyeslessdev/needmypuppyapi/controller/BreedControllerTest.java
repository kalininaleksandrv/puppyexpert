package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Not;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
import java.util.*;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BreedControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BreedController controller;

    private MockMvc mockMvc;

    @Mock
    private BreedService breedService;

    @InjectMocks
    private BreedController breedController;


    @BeforeEach
    void setUp() throws Exception {

        HashMap breedmap = new HashMap();
        breedmap.put("allbreeds", new ArrayList<>(Arrays.asList(
                new Breed((long) 1, "Dog1", "Firstdog"),
                new Breed((long) 2, "Dog2", "Seconddog"),
                new Breed((long) 3, "Dog3", "Thirddog")
        )));
        ResponseEntity<Map<String, List<Breed>>> searchingresult = new ResponseEntity<>(breedmap, HttpStatus.OK);
        when(breedService.getAllBreedsOrderedById()).thenReturn(searchingresult);

        mockMvc = MockMvcBuilders.standaloneSetup(breedController)
                .build();
    }

    @Test
    void testingContextLoad() throws Exception {
        assertNotNull(controller, "BreedController is NULL");
    }

    @Test
    void getAllBreedsById() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/breeds").accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasKey("allbreeds")))
                .andExpect(jsonPath("$.allbreeds.[*]", Matchers.iterableWithSize(3)))
                .andExpect(jsonPath("$.allbreeds.[0]", Matchers.instanceOf(LinkedHashMap.class)))
                .andExpect(jsonPath("$.allbreeds.[0].title", Matchers.containsString("Dog1")));
    }

    @Test
    void getAllBreedsOrderedByTitle() throws Exception {
    }

    @Test
    void getBreedById() throws Exception {
    }

    @Test
    void faveBreed() throws Exception {
    }

    @Test
    void getFilteredBreeds() throws Exception {
    }
}