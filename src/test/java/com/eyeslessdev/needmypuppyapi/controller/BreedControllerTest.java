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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.doubleThat;
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

       ResultActions resultActions = mockMvcReal.perform(get("/breeds/bytitle")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.iterableWithSize(350)))
                .andExpect(jsonPath("$.[0].title", Matchers.startsWith("А")))
                .andExpect(jsonPath("$.[349].title", Matchers.startsWith("Я")));
//                .andExpect(jsonPath("$.[*].id", Matchers.arrayContainingInAnyOrder(range)));
        JSONArray myjarray = JsonPath.parse(resultActions.andReturn().getResponse().getContentAsString()).read("$.[*].title");
        System.out.println(myjarray.toString());
    }

    @Test
    void getAllBreedsOrderedByTitleAsArray() throws Exception {

        ResultActions resultActions = mockMvcReal.perform(get("/breeds/bytitle")
                .accept(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);

        //get json array from json using regex in .read()
        JSONArray myjsonArray = JsonPath.parse(resultActions.andReturn().getResponse().getContentAsString()).read("$.[*].title");
        //convert json array to string and then split it to regular array of string
        String [] mytitleArray = myjsonArray.toString().split(",");
//        String [] clearedArray = new String[mytitleArray.length];

        Stream<String> stream = Arrays.stream(mytitleArray);
        stream
                .map(i -> i.replaceAll("\\[", "").replaceAll("\\]",""))
                .forEach(s -> System.out.println(s));


        for (String i : mytitleArray) {
               String str = i;
               str = str.replaceAll("\\[", "").replaceAll("\\]","");
                          }


//        System.out.println(Arrays.toString(mytitleArray));

        assertThat(mytitleArray, not(emptyArray()));
        assertThat(mytitleArray, arrayWithSize(350));

//        String prev = null;
//        String curr = null;
//
//        for (String i : mytitleArray) {
//
//            String substr = i.substring(0, 5);
//
//            if (curr != null ){
//                prev = curr;
//                curr = substr;
//                assertThat(prev, not(greaterThan(curr)));
//            } else curr = substr;
//
//        }



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
    void faveBreed() throws Exception {
    }

    @Test
    void getFilteredBreeds() throws Exception {
    }
}