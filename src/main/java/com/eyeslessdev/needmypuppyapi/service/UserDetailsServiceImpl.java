package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.MyUserPrincipal;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String useremail) {

        //find user by email instead of username
        Optional<User> user = userRepo.findByEmail(useremail);
        if (user.isPresent()) {
            //wrap founded user on userprincipal, get in from optional with .get()
            return new MyUserPrincipal(user.get());
        } else {
            System.out.println("User "+useremail+ " not found"); // TODO: 28.07.2019 add correct logger 
            throw new UsernameNotFoundException("User "+useremail+ " not found");
        }
    }
}
