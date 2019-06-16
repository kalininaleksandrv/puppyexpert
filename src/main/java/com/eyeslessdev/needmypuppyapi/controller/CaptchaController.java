package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.entity.dto.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@CrossOrigin
@RequestMapping("captcha")
public class CaptchaController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String CAPTURL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String recsec;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<CaptchaResponseDto> getBreedById(@RequestBody String clienresp) {

        String url = String.format(CAPTURL, recsec, clienresp);
        System.out.println(url);
        return restTemplate.postForEntity(url, Collections.EMPTY_LIST, CaptchaResponseDto.class);
    }
}
