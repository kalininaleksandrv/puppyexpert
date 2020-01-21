package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Role;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.eyeslessdev.needmypuppyapi.security.CommonConsts;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private UserRepo userRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> findAll() {return userRepo.findAll();}

    public Optional<User> findById(long id) {return userRepo.findById(id);}

    public Optional<User> saveNewUser (User user){
        if (!userRepo.findByEmail(user.getEmail()).isPresent()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (user.getExternalid() == null) {user.setExternalid(UUID
                    .randomUUID()
                    .toString()
                    .concat(CommonConsts.ONUS_AUTH));}
            user.setRoles(Collections.singleton(Role.CREATEDUSER));
            user.setRegistrationtime(System.currentTimeMillis());
            user.setLastvisit(System.currentTimeMillis());

            return Optional.of(userRepo.save(user));
        } else {
            return Optional.empty();
        }
    }

    String getAuthenticatedPrincipalUserName() {
        if (!(getCurrentAuthentication() instanceof AnonymousAuthenticationToken)) {

            Authentication authentication = getCurrentAuthentication();

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

        if (!(getCurrentAuthentication() instanceof AnonymousAuthenticationToken)) {

            return getCurrentAuthentication().getAuthorities();

        } else {
            return Collections.emptyList();
        }
    }

    private Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public List<User> findAllCreated(String status) {
        return userRepo.fetchUserListComparedStatus(status);
    }

    public void setLastVisitTimeToUser(String useremail){
        Optional<User> user = userRepo.findByEmail(useremail);
        if (user.isPresent()){
            user.get().setLastvisit(System.currentTimeMillis());
            userRepo.save(user.get());
        } else {
            System.out.println("no such user to post visit time");
            // TODO: 29.12.2019 log this 
        }
    }

    public Boolean changeStatus(Long id, Set<Role> roles) {

        try {
            Optional<User> updateduseropt = userRepo.findById(id);
            if (updateduseropt.isPresent()){
                updateduseropt.get().getRoles().clear();
                updateduseropt.get().setRoles(roles);
                userRepo.save(updateduseropt.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
            // TODO: 29.12.2019 log this 
        }
    }

}
