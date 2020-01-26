package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.MyUserPrincipal;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepo userRepo;

    private Logger logger;

    public UserDetailsServiceImpl(UserRepo userRepo, Logger logger) {
        this.userRepo = userRepo;
        this.logger = logger;
    }

    @Override
    public UserDetails loadUserByUsername(String useremail) {

        //find user by email instead of username
        Optional<User> user = userRepo.findByEmail(useremail);
        if (user.isPresent()) {
            //wrap founded user on userprincipal, get in from optional with .get()
            return new MyUserPrincipal(user.get());
        } else {
            logger.warn("UserDetailsServiceImpl, " +
                    "loadUserByUsername(String useremail) , " +
                    "User "+useremail+ " not found");
            throw new UsernameNotFoundException("User "+useremail+ " not found");
        }
    }
}
