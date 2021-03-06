package com.eyeslessdev.needmypuppyapi.security;

import com.auth0.jwt.JWT;
import com.eyeslessdev.needmypuppyapi.entity.MyUserPrincipal;
import com.eyeslessdev.needmypuppyapi.entity.User;
import com.eyeslessdev.needmypuppyapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String jwtsecret, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtsecret = jwtsecret;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            //obtain json with    {
            //    "email": "test5@test.com",
            //    "password": "test5"
            //   } and map it to user.class
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            //wrap user with userprincipal
            MyUserPrincipal principalcreds = new MyUserPrincipal(creds);

            //pass parsmeters email (userprincipal returns email instead of name) and password to UserDetailServiceImpl
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            principalcreds.getUsername(),
                            principalcreds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (AuthenticationException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        MyUserPrincipal myuser = (MyUserPrincipal) auth.getPrincipal();

        String[] targetArray = myuser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).distinct().toArray(String[]::new);

        String token = JWT.create()
                .withSubject(myuser.getUsername())
                .withClaim(CommonConsts.ISENABLED_KEY, myuser.isEnabled())
                .withClaim(CommonConsts.REALNAME_KEY, myuser.getRealName())
                .withArrayClaim(CommonConsts.AUTHORITIES_KEY, targetArray)
                .withExpiresAt(new Date(System.currentTimeMillis() + CommonConsts.EXPIRATION_TIME))
                .sign(HMAC512(jwtsecret.getBytes()));

        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader(CommonConsts.HEADER_STRING, CommonConsts.TOKEN_PREFIX + token);

        userService.setLastVisitTimeToUser(myuser.getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        logger.debug("failed authentication while attempting to access");

        //Add more descriptive message
        response.sendError(HttpServletResponse.SC_FORBIDDEN,
                "Неверный email или пароль");
    }

}
