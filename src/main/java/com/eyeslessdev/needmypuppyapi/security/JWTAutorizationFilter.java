package com.eyeslessdev.needmypuppyapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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

            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtsecret.getBytes()))
                    .build()
                    .verify(token.replace(CommonConsts.TOKEN_PREFIX, ""));

                    String user = decodedJWT.getSubject();

            Map<String, Claim> extrainfo = decodedJWT.getClaims();

            if (extrainfo != null){

//                if (extrainfo.get(CommonConsts.EXTERNALID_KEY).endsWith(CommonConsts.ONUS_AUTH)) {
                    System.out.println(extrainfo.get(CommonConsts.EXTERNALID_KEY).asString());
//                } else {
                    System.out.println(extrainfo.get(CommonConsts.ISENABLED_KEY).asBoolean());
                    System.out.println(extrainfo.get(CommonConsts.AUTHORITIES_KEY).asList(String.class));

                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            } else {
                System.out.println("------------->user is null");
                return null;
            }

        } else return null;
    }

    private String getHeader(HttpServletRequest request) {
        return request.getHeader(CommonConsts.HEADER_STRING);
    }

}
