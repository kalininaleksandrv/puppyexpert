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
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        Optional<User> appuser = userRepo.findByEmail(useremail);
        if (appuser.isPresent()){
            User returneduser = new User();
            returneduser.setEmail(appuser.get().getEmail());
            returneduser.setPassword(appuser.get().getPassword());
            returneduser.setRoles(appuser.get().getRoles());
            return returneduser;
        } else {
            throw new UsernameNotFoundException(useremail);
        }

    }


}
