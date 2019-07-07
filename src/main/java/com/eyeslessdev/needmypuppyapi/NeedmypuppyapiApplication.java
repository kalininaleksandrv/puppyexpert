package com.eyeslessdev.needmypuppyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NeedmypuppyapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeedmypuppyapiApplication.class, args);
	}

}
