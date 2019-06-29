package com.eyeslessdev.needmypuppyapi.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
public class AutentificationController {

    @RequestMapping("user")
public Principal user (Principal principal){
        return principal;
    }
}
