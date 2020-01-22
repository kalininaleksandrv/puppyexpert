package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import com.eyeslessdev.needmypuppyapi.entity.BreedRequestFactory;
import com.eyeslessdev.needmypuppyapi.repositories.BreedRequestRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BreedFilterServiceTest {

    @Mock
    private BreedRequestFactory breedRequestFactory;

    @Mock
    private BreedRequestRepo breedRequestRepo;

    @InjectMocks
    private BreedFilterService breedFilterService;

    private static BreedRequest.Builder brBuilder;
    private static Map<String, String> allParam;

    @BeforeAll()
    static void setUp() {

        allParam = new HashMap<>();
        allParam.put("time", "4");
        allParam.put("exp", "4");
        allParam.put("age", "4");

        brBuilder = new BreedRequest.Builder();

    }

    @Test
    void getBreedRequest() {

        when(breedRequestFactory.getBreedRequest(any(Map.class))).thenReturn(brBuilder.time(4).exp(4).age(4).build());

        BreedRequest breedRequest = breedRequestFactory.getBreedRequest(allParam);
        assertThat(breedRequest.getRequestParamsAsString()).contains("BreedRequest", "time=4", "exp=4", "age=4");

        Mockito.verify(breedRequestFactory, Mockito.times(1)).getBreedRequest(allParam);

    }

    @Test
    void getProperBreeds() {
    }

    @Test
    void saveBreedRequest() {

        when(breedRequestFactory.getBreedRequest(any(Map.class))).thenReturn(brBuilder.time(4).exp(4).age(4).build());

        long currentTime = System.currentTimeMillis();
        BreedRequest breedRequest = breedRequestFactory.getBreedRequest(allParam);

        when(breedRequestRepo.save(breedRequest)).thenReturn(breedRequest);

        breedFilterService.saveBreedRequest(breedRequest, "user@test.com");

        ArgumentCaptor<BreedRequest> breedArgumentCaptor = ArgumentCaptor.forClass(BreedRequest.class);
        Mockito.verify(breedRequestRepo).save(breedArgumentCaptor.capture());

        BreedRequest savedBreedRequest = breedArgumentCaptor.getValue();
        long expectedVisitTime = savedBreedRequest.getRequestcreatetime();
        assertThat(expectedVisitTime - currentTime).isLessThan(100);

        Pattern pattern = Pattern.compile("Created by: ([^@]+@[^.]+\\..+), at: ([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d\\s-\\s([0-1]?[0-9]|2?[0-3]):([0-5]\\d)");
        Matcher matcher = pattern.matcher(savedBreedRequest.getCreator());
        assertTrue(matcher.find());
    }
}

