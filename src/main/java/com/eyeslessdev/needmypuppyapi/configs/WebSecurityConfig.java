package com.eyeslessdev.needmypuppyapi.configs;

import com.eyeslessdev.needmypuppyapi.repositories.UserRepo;
import com.eyeslessdev.needmypuppyapi.security.JWTAuthenticationFilter;
import com.eyeslessdev.needmypuppyapi.security.JWTAutorizationFilter;
import com.eyeslessdev.needmypuppyapi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepo userDetailsRepo;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceimpl;

    @Value("${jwttoken.secret}")
    private String jwtsecret;

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/users/**").permitAll()
                .mvcMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin()
//                .loginPage("/login") todo work it out
//                .usernameParameter("email")
//                .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtsecret))
        .addFilter(new JWTAutorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceimpl).passwordEncoder(bCryptPasswordEncoder());
    }

//    @Bean
//    public PrincipalExtractor principalExtractor (UserRepo userRepo){
//        return map -> {
//            return new User();
//        };
//    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
