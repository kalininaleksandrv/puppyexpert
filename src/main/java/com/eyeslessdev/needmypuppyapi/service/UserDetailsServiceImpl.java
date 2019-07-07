package com.eyeslessdev.needmypuppyapi.service;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> appuser = userRepo.findByName(username);
        if (appuser.isPresent()){
            User returneduser = new User();
            returneduser.setName(appuser.get().getUsername());
            returneduser.setPassword(appuser.get().getPassword());
            returneduser.setRoles(appuser.get().getRoles());
            return returneduser;
        } else {
            throw new UsernameNotFoundException(username);
        }

    }
}
