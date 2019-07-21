package com.eyeslessdev.needmypuppyapi.security;

import com.auth0.jwt.JWT;
import com.eyeslessdev.needmypuppyapi.entity.MyUserPrincipal;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;
    private String jwtsecret;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String jwtsecret) {
        this.authenticationManager = authenticationManager;
        this.jwtsecret = jwtsecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        MyUserPrincipal myuser = (MyUserPrincipal) auth.getPrincipal();


        String token = JWT.create()
                .withSubject(myuser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + CommonConsts.EXPIRATION_TIME))
                .sign(HMAC512(jwtsecret.getBytes()));

        System.out.println(token);

        res.addHeader(CommonConsts.HEADER_STRING, CommonConsts.TOKEN_PREFIX + token);
    }
}
