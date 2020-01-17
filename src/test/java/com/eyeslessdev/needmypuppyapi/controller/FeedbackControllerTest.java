package com.eyeslessdev.needmypuppyapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FeedbackControllerTest {

    @Autowired
    FeedbackController feedbackController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController)
                .build();
    }

    @Test
    void testingContextLoad() {
        assertNotNull(feedbackController, "BreedController is NULL");
    }

    @Test
    void getFeedbackById() {
    }

    @Test
    void sendFeedback() {
    }
}