package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserDetailsServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    String knownEmail;
    String unknownEmail;
    User firstUser;

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

        knownEmail = firstUser.getEmail();
        unknownEmail = "unknown@unknown.com";


        when(userRepo.findByEmail(knownEmail)).thenReturn(Optional.of(firstUser));
        when(userRepo.findByEmail(unknownEmail)).thenReturn(Optional.empty());

    }

    @Test
    void loadUserByUsername() {
        UserDetails knownUser = userDetailsService.loadUserByUsername(knownEmail);

        assertEquals(knownUser.getUsername(), firstUser.getEmail());
    }

    @Test
    void loadUserByUsername_caseUnknownEmail() {

        Exception exception = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(unknownEmail));

        assertTrue(exception.getMessage().contains("User "+unknownEmail+ " not found"));
    }
}