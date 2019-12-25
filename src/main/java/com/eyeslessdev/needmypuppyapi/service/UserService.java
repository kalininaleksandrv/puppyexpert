package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Boolean changeStatus(Long id, Set<Role> roles) {

        try {
            Optional<User> updateduseropt = userRepo.findById(id);
            if (updateduseropt.isPresent()){
                updateduseropt.get().getRoles().clear();
                updateduseropt.get().setRoles(roles);
                userRepo.save(updateduseropt.get());
                return true;
            } else {return false;}
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<List<User>> findAll() {return Optional.ofNullable(userRepo.findAll());}

    public Optional<User> findById(long id) {return userRepo.findById(id);}

    String getAuthenticatedPrincipalUserName() {
        if (!(getCuternAuthentication() instanceof AnonymousAuthenticationToken)) {

            Authentication authentication = getCuternAuthentication();

            if (authentication == null) return "anonimous";

            Optional<User> user =  userRepo.findByEmail(authentication.getName());

            if (user.isPresent()){
                return user.get().getName();
            } else return "anonimous";

        } else {
            return "anonimous";
        }
    }


    Collection<? extends GrantedAuthority> getAuthenticatedPrincipalUserRole() {

        if (!(getCuternAuthentication() instanceof AnonymousAuthenticationToken)) {

            return getCuternAuthentication().getAuthorities();

        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Authentication getCuternAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Optional<List<User>> findAllCreated(String status) {

        return userRepo.fetchCustomQuery(status);
    }

    public void setLastVisitTimeToUser(String useremail){
        Optional<User> user = userRepo.findByEmail(useremail);
        if (user.isPresent()){
            user.get().setLastvisit(System.currentTimeMillis());
            userRepo.save(user.get());
        } else {
            System.out.println("no such user to post visit time");
        }
    }


}
