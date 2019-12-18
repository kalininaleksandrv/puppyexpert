package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.entity.BreedTest;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
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


    @SuppressWarnings("rawtypes")
    @BeforeEach
    void setUp() {

        HashMap breedmap = new HashMap();
        breedmap.put("allbreeds", new ArrayList<>(Arrays.asList(
                new BreedTest((long) 1, "Dog1", "Firstdog"),
                new BreedTest((long) 2, "Dog2", "Seconddog"),
                new BreedTest((long) 3, "Dog3", "Thirddog")
        )));
        ResponseEntity searchingresult = new ResponseEntity<>(breedmap, HttpStatus.OK);
        when(breedService.getAllBreedsOrderedById()).thenReturn(searchingresult);

        when(breedService.faveBreedById(anyLong())).thenReturn(searchingresult);

        mockMvc = MockMvcBuilders.standaloneSetup(breedController)
                .build();

        mockMvcReal = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void testingContextLoad() {
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

       ResultActions resultActions = mockMvcReal.perform(get("/breeds/bytitle")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.iterableWithSize(350)))
                .andExpect(jsonPath("$.[0].title", Matchers.startsWith("А")))
                .andExpect(jsonPath("$.[349].title", Matchers.startsWith("Я")));
    }

    @Test
    void getAllBreedsOrderedByTitleCheckOrder() throws Exception {

        ResultActions resultActions = mockMvcReal.perform(get("/breeds/bytitle")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        //get json array from json using regex in .read()
        JSONArray myjsonArray = JsonPath.parse(resultActions
                .andReturn()
                .getResponse()
                .getContentAsString())
                .read("$.[*].title");

        //convert json array to string and then split it to regular array of string
        String [] mytitleArray = myjsonArray.toString().split(",");

        //remove square brackets and return array for further operations
        String[] resultArray = Arrays.stream(mytitleArray)
                .map(i -> i
                        .replaceAll("\\[", "")
                        .replaceAll("]", "")
                        .replaceAll("\"", "")
                        .replaceAll("ё", "е")
                        .replaceAll("е́", "е")
                        .replaceAll("о́", "о")
                )
                .toArray(String[]::new);

        assertThat(resultArray, not(emptyArray()));
        assertThat(resultArray, arrayWithSize(350));

        for (int i = 0; i < resultArray.length - 1; i++)
        {
                int k=i+1;
                assertThat(resultArray[i].substring(0, 2), not(greaterThan(resultArray[k].substring(0, 2))));
        }
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

        ResultActions resultActions400 = mockMvcReal.perform(get("/breeds/dhmtdyhmjkdtm")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions400);

        resultActions400
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void faveBreed() throws Exception {

        ResultActions resultActions = mockMvcReal.perform(get("/breeds/faved/3")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActions404 = mockMvcReal.perform(get("/breeds/faved/999")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions404);

        resultActions404
                .andDo(print())
                .andExpect(status().isNotFound());

        ResultActions resultActions400 = mockMvcReal.perform(get("/breeds/faved/dhmtdyhmjkdtm")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions400);

        resultActions400
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void faveBreedCheckIncreasing() throws Exception {

        ResultActions resultActionsFirst = mockMvcReal.perform(get("/breeds/3")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActionsFirst);

        Integer firstDogParam = JsonPath.parse(resultActionsFirst
                .andReturn()
                .getResponse()
                .getContentAsString())
                .read("$.favorite");

        ResultActions resultActionsNext = mockMvcReal.perform(get("/breeds/faved/3")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActionsNext);

        resultActionsNext
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActionsLast = mockMvcReal.perform(get("/breeds/3")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActionsLast);

        Integer lastDogParam = JsonPath.parse(resultActionsLast
                .andReturn()
                .getResponse()
                .getContentAsString())
                .read("$.favorite");

        assertThat(firstDogParam, lessThan(lastDogParam));

    }

    @Test
    void getFilteredBreeds() throws Exception {

        ResultActions resultActions = mockMvcReal.perform(get("/breeds/filtered")
                .param("exp", "1")
                .param("time", "1")
                .param("age", "1")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk());

        ResultActions resultActionsNoParams = mockMvcReal.perform(get("/breeds/filtered")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActionsNoParams);

        resultActionsNoParams
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}