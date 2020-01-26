package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.FeedbackRepo;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
class FeedbackServiceTest {

    @Mock
    FeedbackRepo feedbackRepo;

    @Mock
    UserService userService;

    @Mock
    UserRepo userRepo;

    @Mock
    Logger logger;

    @InjectMocks
    FeedbackService feedbackService;

    private static ArrayList<Feedback> listOfFeedback;

    Feedback feedbackOne;
    Feedback feedbackTwo;
    Feedback feedbackThree;

    User firstUser;

    Map<String, List<Integer>> income;

    @BeforeEach
    void setUp() {

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setName("myuser");
        firstUser.setPassword("myuser");
        firstUser.setEmail("myuser@test.com");
        firstUser.setRoles(roles);

        feedbackOne = new Feedback();
        feedbackOne.setId(99L);
        feedbackOne.setDogid(3L);
        feedbackOne.setTitle("my feedback one");
        feedbackOne.setDescription("I'am awesome");
        feedbackOne.setEmail("admin@test.com");

        feedbackTwo = new Feedback();
        feedbackTwo.setId(99L);
        feedbackTwo.setDogid(3L);
        feedbackTwo.setTitle("my feedback two");
        feedbackTwo.setDescription("I'am too awesome");
        feedbackTwo.setEmail("some@some.com");
        feedbackTwo.setUsername("someone");

        feedbackThree = new Feedback();
        feedbackThree.setId(99L);
        feedbackThree.setDogid(4L);
        feedbackThree.setTitle("my feedback three");
        feedbackThree.setDescription("I'am so much awesome");
        feedbackThree.setEmail("111@some.com");
        feedbackThree.setUsername("111");

        listOfFeedback = new ArrayList<>(Arrays.asList(feedbackOne, feedbackTwo, feedbackThree));

        income = new HashMap<>();
        income.put("DELETE", new ArrayList<>(Arrays.asList(1, 2, 3)));
        income.put("UPDATE", new ArrayList<>(Arrays.asList(4, 5, 6)));
    }

    @Test
    void findByDogId() {

        feedbackOne.setIsModerated(1);
        feedbackTwo.setIsModerated(0);
        feedbackThree.setIsModerated(0);


        long arg = 3L;

        List<Feedback> filteredFeedbackList = listOfFeedback.stream()
                .filter(l -> l.getDogid() == arg)
                .collect(Collectors.toList());

        when(feedbackRepo.findTop10ByDogidOrderByCommenttimeDesc(anyLong())).thenReturn(filteredFeedbackList);
        List<Feedback> testingFeedback = feedbackService.findByDogId(arg);

        assertNotNull(testingFeedback);
        assertThat(testingFeedback.get(0)).hasSameClassAs(listOfFeedback.get(0));
        assertThat(testingFeedback).containsOnly(feedbackOne);//feedbackTwo wont pass cause of no moderated status
        assertThat(testingFeedback).extracting("email").containsOnly("access not allowed");

        Mockito.verify(feedbackRepo, Mockito.times(1)).findTop10ByDogidOrderByCommenttimeDesc(arg);
    }

    @Test
    void saveFeedback_caseSetUpModeratedStatus() {

        Collection<Role> collectionsOfRoles = Collections.singleton(Role.USER);

        OngoingStubbing<Collection<? extends GrantedAuthority>> stub = when(userService.getAuthenticatedPrincipalUserRole());
        stub.thenReturn(collectionsOfRoles);
        when(userService.getAuthenticatedPrincipalUserEmail()).thenReturn("myuser@test.com");
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(firstUser));

        Boolean isFeedbackSaved = feedbackService.saveFeedback(feedbackOne);

        assertTrue(isFeedbackSaved);

        Mockito.verify(feedbackRepo, Mockito.times(1)).save(feedbackOne);

        ArgumentCaptor<Feedback> breedArgumentCaptor = ArgumentCaptor.forClass(Feedback.class);
        Mockito.verify(feedbackRepo).save(breedArgumentCaptor.capture());

        Feedback savedUser = breedArgumentCaptor.getValue();
        assertThat(savedUser.isModerated()).isEqualTo(1);
        assertThat(savedUser.getCommenttime() - System.currentTimeMillis()).isLessThan(100);
        assertNotNull(savedUser.getCommenttimestr());
        assertEquals(savedUser.getEmail(), "myuser@test.com");
        assertEquals(savedUser.getUsername(), "myuser");


    }

    @Test
    void saveFeedback_caseSetUpUnmoderatedStatus() {

        Collection<Role> collectionsOfRoles = Collections.singleton(Role.CREATEDUSER);

        OngoingStubbing<Collection<? extends GrantedAuthority>> stub = when(userService.getAuthenticatedPrincipalUserRole());
        stub.thenReturn(collectionsOfRoles);

        when(userService.getAuthenticatedPrincipalUserEmail()).thenReturn("myuser@test.com");
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(firstUser));

        Boolean isFeedbackSaved = feedbackService.saveFeedback(feedbackThree);

        assertTrue(isFeedbackSaved);

        Mockito.verify(feedbackRepo, Mockito.times(1)).save(feedbackThree);

        ArgumentCaptor<Feedback> breedArgumentCaptor = ArgumentCaptor.forClass(Feedback.class);
        Mockito.verify(feedbackRepo).save(breedArgumentCaptor.capture());

        Feedback savedUser = breedArgumentCaptor.getValue();
        assertThat(savedUser.isModerated()).isEqualTo(0);
        assertThat(savedUser.getCommenttime() - System.currentTimeMillis()).isLessThan(100);
        assertNotNull(savedUser.getCommenttimestr());
        assertEquals(savedUser.getEmail(), "myuser@test.com");
        assertEquals(savedUser.getUsername(), "myuser");

    }

    @Test
    void findUnmoderatedFeedback_inCaseUnmoderated() {

        feedbackOne.setIsModerated(1);
        feedbackTwo.setIsModerated(0);
        feedbackThree.setIsModerated(0);

        List<Feedback> filteredFeedbackList = listOfFeedback.stream()
                .filter(l -> l.isModerated() == 0)
                .collect(Collectors.toList());

        when(feedbackRepo.findByIsmoderated(anyInt())).thenReturn(filteredFeedbackList);
        List<Feedback> testingFeedback = feedbackService.findUnmoderatedFeedback(0);

        assertNotNull(testingFeedback);
        assertThat(testingFeedback).containsOnly(feedbackTwo, feedbackThree);

        Mockito.verify(feedbackRepo, Mockito.times(1)).findByIsmoderated(0);
    }

    @Test
    void findUnmoderatedFeedback_inCaseModerated() {

        feedbackOne.setIsModerated(1);
        feedbackTwo.setIsModerated(0);
        feedbackThree.setIsModerated(0);

        List<Feedback> filteredFeedbackList = listOfFeedback.stream()
                .filter(l -> l.isModerated() == 1)
                .collect(Collectors.toList());

        when(feedbackRepo.findByIsmoderated(anyInt())).thenReturn(filteredFeedbackList);
        List<Feedback> testingFeedback = feedbackService.findUnmoderatedFeedback(0);

        assertNotNull(testingFeedback);
        assertThat(testingFeedback).containsOnly(feedbackOne);

        Mockito.verify(feedbackRepo, Mockito.times(1)).findByIsmoderated(0);
    }

    @Test
    void deleteModeratedFromDb() {

        when(feedbackRepo.deleteFeedbackById(Collections.singleton(anyLong()))).thenReturn(1);

        feedbackService.deleteModeratedFromDb(income);

        Mockito.verify(feedbackRepo, Mockito.times(1)).deleteFeedbackById(any(Collection.class));

        ArgumentCaptor<Set<Long>> breedArgumentCaptor = ArgumentCaptor.forClass(HashSet.class);
        Mockito.verify(feedbackRepo).deleteFeedbackById(breedArgumentCaptor.capture());
        Set<Long>capturedValues = breedArgumentCaptor.getValue();

        assertThat(capturedValues).containsOnly(1L, 2L, 3L);

    }

    @Test
    void updateModeratedInDb() {

        when(feedbackRepo.deleteFeedbackById(Collections.singleton(anyLong()))).thenReturn(1);

        feedbackService.updateModeratedInDb(income);

        Mockito.verify(feedbackRepo, Mockito.times(1)).updateFeedbackById(anyInt(), any(Collection.class));

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Set<Long>> setArgumentCaptor = ArgumentCaptor.forClass(HashSet.class);

        Mockito.verify(feedbackRepo).updateFeedbackById(integerArgumentCaptor.capture(), setArgumentCaptor.capture());

        Integer capturedValuesIsModerated = integerArgumentCaptor.getValue();
        Set<Long> capturedValuesMap = setArgumentCaptor.getValue();

        assertThat(capturedValuesIsModerated).isEqualTo(1);
        assertThat(capturedValuesMap).containsOnly(4L, 5L, 6L);
    }
}