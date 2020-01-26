package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")

class AdminControllerTest {

    @Autowired
    AdminController adminController;

    private MockMvc mockMvc;

    private User firstUser;

    private ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .build();

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        firstUser = new User();
        firstUser.setId(2L);
        firstUser.setName("myuser");
        firstUser.setPassword("myuser");
        firstUser.setEmail("myuser@test.com");
        firstUser.setRoles(roles);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    void testingContextLoad() {
        assertNotNull(adminController, "BreedController is NULL");
    }

    @Test
    void getFeedbackByStatus() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/admin/messagestomod")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue(Collection.class)));
    }

    @Test
    void moderateMessages() {
        //covered by unit test in the UserServiceTest
    }

    @Test
    void findAll() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/admin/getallusers")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue(Collection.class)));
    }

    @Test
    void findAllByStatus() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/admin/getallusersbystatus")
                .param("status", "CREATEDUSER")
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.iterableWithSize(2)))
                .andExpect(jsonPath("$.[*].roles.[*]", Matchers.hasItem("CREATEDUSER")))
                .andExpect(jsonPath("$.[*].roles.[*]", Matchers.not(Matchers.hasItem("USER"))))
                .andExpect(jsonPath("$.[*].roles.[*]", Matchers.not(Matchers.hasItem("ADMIN"))));
    }

    @Test
    void findAllByStatus_caseIncorrectStatus() throws Exception {
        String status = "SOMEUNEXPECTEDSTATUS";
        ResultActions resultActions = mockMvc.perform(get("/admin/getallusersbystatus")
                .param("status", status)
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserById() throws Exception {

        int id = 1;
        ResultActions resultActions = mockMvc.perform(get("/admin/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(id)));
    }

    @Test
    void getUserById_caseIncorrectId() throws Exception {

        int id = 0;
        ResultActions resultActions = mockMvc.perform(get("/admin/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserById() throws Exception {
        int id = 3;
        Map<String, Set<Role>> roles = new HashMap<>();
        roles.put("roles", firstUser.getRoles());
        String requestJson = objectWriter.writeValueAsString(roles);

        ResultActions resultActions = mockMvc.perform(post("/admin/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateUserById_caseIncorrectId() throws Exception {
        int id = 999;
        Map<String, Set<Role>> roles = new HashMap<>();
        roles.put("roles", firstUser.getRoles());
        String requestJson = objectWriter.writeValueAsString(roles);

        ResultActions resultActions = mockMvc.perform(post("/admin/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateUserById_caseIncorrectRole() throws Exception {
        int id = 37;

        Map<String, Set<String>> roles = new HashMap<>();
        roles.put("roles", Collections.singleton("11111111"));
        String requestJson = objectWriter.writeValueAsString(roles);

        ResultActions resultActions = mockMvc.perform(post("/admin/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8));


        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}