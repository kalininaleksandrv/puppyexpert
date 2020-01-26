package com.eyeslessdev.needmypuppyapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eyeslessdev.needmypuppyapi.entity.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTAutorizationFilter extends BasicAuthenticationFilter {

    private String jwtsecret;

    public JWTAutorizationFilter(AuthenticationManager authenticationManager, String jwtsecret) {
        super(authenticationManager);
        this.jwtsecret = jwtsecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = getHeader(request);

        if(header == null || !header.startsWith(CommonConsts.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationtoken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
        chain.doFilter(request, response);

    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = getHeader(request);
        if(token != null){

            DecodedJWT decodedJWT = null;

            try {
                decodedJWT = JWT.require(Algorithm.HMAC512(jwtsecret.getBytes()))
                        .build()
                        .verify(token.replace(CommonConsts.TOKEN_PREFIX, ""));

            } catch (JWTVerificationException | IllegalArgumentException e) {
                e.printStackTrace();
            }

            if (decodedJWT !=null){

                String user = decodedJWT.getSubject();

                Map<String, Claim> extrainfo = decodedJWT.getClaims();

                return new UsernamePasswordAuthenticationToken(user, null, getRolesFromExtrainfo(extrainfo));
            } else {
                return null;
            }

        } else return null;
    }

    //extract ROLES from token, parse it and convert to ROLE ENUM
    private ArrayList<Role> getRolesFromExtrainfo(Map<String, Claim> extrainfo) {
        return (ArrayList<Role>) extrainfo.get(CommonConsts.AUTHORITIES_KEY).asList(String.class)
                    .stream()
                    .map(val -> getEnumFromString(Role.class, val))
                    .collect(Collectors.toList());
    }

    private String getHeader(HttpServletRequest request) {
        return request.getHeader(CommonConsts.HEADER_STRING);
    }

    private static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        return null;
    }

}
