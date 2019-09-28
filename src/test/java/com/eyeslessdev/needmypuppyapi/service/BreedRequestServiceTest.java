package com.eyeslessdev.needmypuppyapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BreedRequestServiceTest {

    BreedRequestService breedRequestService;

    @BeforeEach
    void setUp() {
        this.breedRequestService = new BreedRequestService();
    }

    @Test
    void saveBreedRequest() {
        System.out.println("I must use mockito");
    }
}