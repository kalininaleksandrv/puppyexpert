package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedTest;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BreedControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BreedController controller;

    private MockMvc mockMvc;

    private MockMvc mockMvcReal;

    @Mock
    private BreedService breedService;

    @InjectMocks
    private BreedController breedController;


    @BeforeEach
    void setUp() throws Exception {



        HashMap breedmap = new HashMap();
        breedmap.put("allbreeds", new ArrayList<>(Arrays.asList(
                new BreedTest((long) 1, "Dog1", "Firstdog"),
                new BreedTest((long) 2, "Dog2", "Seconddog"),
                new BreedTest((long) 3, "Dog3", "Thirddog")
        )));
        ResponseEntity<Map<String, List<? extends Breed>>> searchingresult
                = new ResponseEntity<>(breedmap, HttpStatus.OK);
        when(breedService.getAllBreedsOrderedById()).thenReturn(searchingresult);

        mockMvc = MockMvcBuilders.standaloneSetup(breedController)
                .build();

        mockMvcReal = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void testingContextLoad() throws Exception {
        assertNotNull(controller, "BreedController is NULL");
    }

    @Test
    void getAllBreedsById() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/breeds")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasKey("allbreeds")))
                .andExpect(jsonPath("$.allbreeds.[*]", Matchers.iterableWithSize(3)))
                .andExpect(jsonPath("$.allbreeds.[0].title", Matchers.containsString("Dog1")));
    }

    @Test
    void getAllBreedsByIdIntegration() throws Exception {

        ResultActions resultActions = mockMvcReal.perform(get("/breeds")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasKey("Список всех пород")))
                .andExpect(jsonPath("$.[*].[*]", Matchers.iterableWithSize(350)))
                .andExpect(jsonPath("$.['Список всех пород'].[0].id", Matchers.is(2)))
                .andExpect(jsonPath("$.['Список всех пород'].[349].title", Matchers.containsString("Хаски")));

    }

    @Test
    void getAllBreedsOrderedByTitle() throws Exception {
    }

    @Test
    void getBreedById() throws Exception {
        ResultActions resultActions = mockMvcReal.perform(get("/breeds/3")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(3)))
                .andExpect(jsonPath("$.title", Matchers.containsString("Австралийская овчарка (Аусси)")));

        ResultActions resultActions404 = mockMvcReal.perform(get("/breeds/999")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions404);

        resultActions404
                .andDo(print())
                .andExpect(status().isNotFound());

        ResultActions resultActions400 = mockMvcReal.perform(get("/breeds/3dhmtdyhmjkdtm")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions400);

        resultActions400
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBreedByIdWithString() throws Exception {

        ResultActions resultActions = mockMvcReal.perform(get("/breeds/3")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Breed myBreed = objectMapper.readValue(contentAsString, Breed.class);

        System.out.printf(myBreed.toString());
    }

    @Test
    void faveBreed() throws Exception {
    }

    @Test
    void getFilteredBreeds() throws Exception {
    }
}