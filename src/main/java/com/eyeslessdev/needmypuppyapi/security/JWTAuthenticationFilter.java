package com.eyeslessdev.needmypuppyapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eyeslessdev.needmypuppyapi.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${jwttoken.secret}")
    private String jwtsecret;

    private AuthenticationManager authenticationManager;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {


        try {

            User incominguser = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    incominguser.getEmail(), // TODO: 14.07.19 в оригинальном гайде тут username 
                    incominguser.getPassword(),
                    new ArrayList<>()));


        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User)authResult.getPrincipal()).getExternalid())
                .withExpiresAt(new Date(System.currentTimeMillis()+CommonConsts.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(jwtsecret.getBytes()));

        response.addHeader(CommonConsts.HEADER_STRING, CommonConsts.TOKEN_PREFIX+token);

    }
}
