package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FeedbackServiceTest {

    @Mock
    FeedbackRepo feedbackRepo;

    @InjectMocks
    FeedbackService feedbackService;

    private static ArrayList<Feedback> listOfFeedback;

    Feedback feedbackOne;
    Feedback feedbackTwo;
    Feedback feedbackThree;

    @BeforeEach
    void setUp() {
        feedbackOne = new Feedback();
        feedbackOne.setId(99L);
        feedbackOne.setDogid(3L);
        feedbackOne.setTitle("my feedback one");
        feedbackOne.setDescription("I'am awesome");
        feedbackOne.setEmail("admin@test.com");
        feedbackOne.setUsername("admin");
        feedbackOne.setCommenttime(1579267967549L);
        feedbackOne.setCommenttimestr("01/17/2020 16:32:47");
        feedbackOne.setIsModerated(1);

        feedbackTwo = new Feedback();
        feedbackTwo.setId(99L);
        feedbackTwo.setDogid(3L);
        feedbackTwo.setTitle("my feedback two");
        feedbackTwo.setDescription("I'am too awesome");
        feedbackTwo.setEmail("some@some.com");
        feedbackTwo.setUsername("someone");
        feedbackTwo.setCommenttime(1579267967549L);
        feedbackTwo.setCommenttimestr("01/17/2020 16:32:47");
        feedbackTwo.setIsModerated(0);

        feedbackThree = new Feedback();
        feedbackThree.setId(99L);
        feedbackThree.setDogid(4L);
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
    void findByDogId() {

        long arg = 3L;

        List<Feedback> filteredFeedbackList = listOfFeedback.stream()
                .filter(l -> l.getDogid() == arg)
                .collect(Collectors.toList());

        when(feedbackRepo.findTop10ByDogidOrderByCommenttimeDesc(anyLong())).thenReturn(filteredFeedbackList);
        List<Feedback> testingFeedback = feedbackService.findByDogId(arg);

        assertNotNull(testingFeedback);
        assertThat(testingFeedback.get(0)).hasSameClassAs(listOfFeedback.get(0));
        assertThat(testingFeedback).containsOnly(feedbackOne); //feedbackTwo wont pass cause of no moderated status
    }

    @Test
    void saveFeedback() {
    }

    @Test
    void findUnmoderatedFeedback_inCaseUnmoderated() {
    }

    @Test
    void findUnmoderatedFeedback_inCaseModerated() {
    }

    @Test
    void deleteModeratedFromDb() {
    }

    @Test
    void updateModeratedInDb() {
    }
}