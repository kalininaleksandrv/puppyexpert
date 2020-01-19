package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;

class FeedbackServiceTest {

    @Mock
    FeedbackRepo feedbackRepo;

    @InjectMocks
    FeedbackService feedbackService;

    private static ArrayList<Feedback> listOfFeedback;

    @BeforeEach
    void setUp() {
        Feedback feedbackOne = new Feedback();
        feedbackOne.setId(99L);
        feedbackOne.setDogid(3L);
        feedbackOne.setTitle("my feedback one");
        feedbackOne.setDescription("I'am awesome");
        feedbackOne.setEmail("admin@test.com");
        feedbackOne.setUsername("admin");
        feedbackOne.setCommenttime(1579267967549L);
        feedbackOne.setCommenttimestr("01/17/2020 16:32:47");
        feedbackOne.setIsModerated(1);

        Feedback feedbackTwo = new Feedback();
        feedbackTwo.setId(99L);
        feedbackTwo.setDogid(3L);
        feedbackTwo.setTitle("my feedback two");
        feedbackTwo.setDescription("I'am too awesome");
        feedbackTwo.setEmail("some@some.com");
        feedbackTwo.setUsername("someone");
        feedbackTwo.setCommenttime(1579267967549L);
        feedbackTwo.setCommenttimestr("01/17/2020 16:32:47");
        feedbackTwo.setIsModerated(0);

        Feedback feedbackThree = new Feedback();
        feedbackThree.setId(99L);
        feedbackThree.setDogid(3L);
        feedbackThree.setTitle("my feedback three");
        feedbackThree.setDescription("I'am so much awesome");
        feedbackThree.setEmail("111@some.com");
        feedbackThree.setUsername("111");
        feedbackThree.setCommenttime(1579267967549L);
        feedbackThree.setCommenttimestr("01/17/2020 16:32:47");
        feedbackThree.setIsModerated(0);

        listOfFeedback = new ArrayList<>(Arrays.asList(feedbackOne, feedbackTwo, feedbackThree));
    }

    @Test
    void findByDogid() {
    }

    @Test
    void saveFeedback() {
    }

    @Test
    void findUnmoderatedFeedback() {
    }

    @Test
    void deleteModeratedFromDb() {
    }

    @Test
    void updateModeratedInDb() {
    }
}