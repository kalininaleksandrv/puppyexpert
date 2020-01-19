package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class FeedbackControllerTest {

    @Autowired
    FeedbackController feedbackController;

    private MockMvc mockMvc;

    private Feedback feedback, incorrectFeedback;

    private ObjectWriter objectWriter;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController)
                .build();

        feedback = new Feedback();
        feedback.setId(99L);
        feedback.setDogid(3L);
        feedback.setTitle("my feedback");
        feedback.setDescription("I'am awesome");
        feedback.setEmail("some@some.com");
        feedback.setUsername("admin");
        feedback.setCommenttime(1579267967549L);
        feedback.setCommenttimestr("01/17/2020 16:32:47");
        feedback.setIsModerated(1);

        incorrectFeedback = new Feedback();
        incorrectFeedback.setId(99L);
        incorrectFeedback.setDogid(3L);
        incorrectFeedback.setTitle("a");
        incorrectFeedback.setDescription("b");
        incorrectFeedback.setEmail("c");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    void testingContextLoad() {
        assertNotNull(feedbackController, "FeedbackController is NULL");
    }

    @Test
    void getFeedbackById() throws Exception {
        int id = 3;
        ResultActions resultActions = mockMvc.perform(get("/feedback/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.iterableWithSize(2)))
                .andExpect(jsonPath("$.[0].dogid", Matchers.is(id)));
    }

    @Test
    void getFeedbackById_caseNotPresentId() throws Exception {
        int id = 999;
        ResultActions resultActions = mockMvc.perform(get("/feedback/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.iterableWithSize(0)));
    }

    @Test
    @WithUserDetails("admin@test.com")
    void sendFeedbackNoErrors() throws Exception {

        String requestJson = objectWriter.writeValueAsString(feedback);

        ResultActions resultActions = mockMvc.perform(post("/feedback")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());

        assertNotNull(resultActions);
    }

    @Test
    void sendFeedbackBindingResultHasErrors() throws Exception {

        String requestJson = objectWriter.writeValueAsString(incorrectFeedback);

        ResultActions resultActions = mockMvc.perform(post("/feedback")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());

        assertNotNull(resultActions);

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[*]", Matchers.iterableWithSize(3)))
                .andExpect(jsonPath("$.[*]", Matchers.containsInAnyOrder(
                        "must be a well-formed email address",
                        "size must be between 4 and 64",
                        "size must be between 5 and 500")));
    }
}