package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import com.eyeslessdev.needmypuppyapi.service.BreedService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BreedControllerTest {

    @Autowired
    private BreedController controller;

    private MockMvc mockMvc;

    @Mock
    private BreedService breedService;

    @InjectMocks
    private BreedController breedController;


    @BeforeEach
    void setUp() throws Exception {
        Breed breedone = new Breed((long) 1, "Dog1", "Firstdog");
        Breed breedtwo = new Breed((long) 2, "Dog2", "Seconddog");
        Breed breedthree = new Breed((long) 3, "Dog3", "Thirddog");
        Map <String, List<Breed>> breedmap = new HashMap();
        breedmap.put("allbreeds", new ArrayList<>(Arrays.asList(breedone, breedtwo, breedthree)));
        ResponseEntity<Map<String, List<Breed>>> searchingresult = new ResponseEntity<>(breedmap, HttpStatus.OK);
        when(breedService.getAllBreedsOrderedById()).thenReturn(searchingresult);

        mockMvc = MockMvcBuilders.standaloneSetup(breedController)
                .build();
    }

    @Test
    void testingContextLoad() throws Exception {
        Assertions.assertNotNull(controller, "BreedController is NULL");
    }

    @Test
    public void testHelloWorldJson() throws Exception {


        mockMvc.perform(get("/breeds/json")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasKey("mybreedmap")))
                .andExpect(jsonPath("$.mybreedmap", Matchers.iterableWithSize(3)))
                .andExpect(jsonPath("$.mybreedmap.[0]", Matchers.instanceOf(String.class)))
                .andExpect(jsonPath("$.mybreedmap.[0]", Matchers.containsString("One")));

    }

    @Test
    void getAllBreedsById() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/breeds").accept(MediaType.APPLICATION_JSON));

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasKey("Список всех пород")))
                .andExpect(jsonPath("$.allbreeds.[0]", Matchers.instanceOf(Breed.class)));
//                .andExpect(jsonPath("$.Список всех пород", Matchers.iterableWithSize(3)));
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