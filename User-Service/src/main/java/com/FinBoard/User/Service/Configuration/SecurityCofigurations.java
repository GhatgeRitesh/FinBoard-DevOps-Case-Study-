package com.FinBoard.User.Service.Configuration;


import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Service.ClientService;

import com.FinBoard.User.Service.Service.CustomUserDetailService;
import com.FinBoard.User.Service.Service.JWTFilterService;
import com.FinBoard.User.Service.Service.UserDetailsServiceSecurity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityCofigurations {

    @Autowired
    private ClientService clientService;
    @Autowired
    private UserDetailsServiceSecurity userDetailsServiceSecurity;

    @Autowired
    private JWTFilterService jwtFilterService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        System.out.println("Security Filter Chain Activated");

//        .requestMatchers("/user/**").authenticated()
        //stateless Authentication
        http.csrf( customizer -> customizer.disable() ).authorizeHttpRequests(request -> request .requestMatchers("/user/**").authenticated().anyRequest()
                .permitAll()).httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilterService, UsernamePasswordAuthenticationFilter.class);

        return http.build();

//        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz.requestMatchers("/user/**").authenticated().anyRequest().permitAll());

//        http.formLogin(form -> form
//                .loginPage("/login")
//                .loginProcessingUrl("/login").permitAll()
//                .defaultSuccessUrl("/User-DashBoard.html")
//                .failureUrl("/login.html")
//                .usernameParameter("userEmail")
//                .passwordParameter("userPassword"));
//
//        http.httpBasic(Customizer.withDefaults());

//        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceSecurity);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
