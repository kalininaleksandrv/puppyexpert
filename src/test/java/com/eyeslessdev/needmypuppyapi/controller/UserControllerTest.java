package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    private User firstUser;

    private User secondUser;

    private ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();

        firstUser = new User();
        firstUser.setName("myuser");
        firstUser.setPassword("myuser");
        firstUser.setEmail("myuser@test.com");

        secondUser = new User();
        secondUser.setName("user100");
        secondUser.setPassword("user100");
        secondUser.setEmail("user100@test.com");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    void testingContextLoad() {
        assertNotNull(userController, "BreedController is NULL");
    }

    @Test
    void signUp() throws Exception {

        String requestJson = objectWriter.writeValueAsString(secondUser);

        ResultActions resultActions = mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());

        assertNotNull(resultActions);

    }

    @Test
    void signUpError() throws Exception {

        String requestJson = objectWriter.writeValueAsString(firstUser);

        ResultActions resultActions = mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Matchers.containsString("Извините, этот email уже занят")));
    }


}


