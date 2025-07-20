package com.FinBoard.User.Service.Configuration;


import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Service.ClientService;
import com.FinBoard.User.Service.Service.CustomUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityCofigurations {

    @Autowired
    private ClientService clientService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        System.out.println("Security Filter Chain Activated");
        http.csrf(csrf-> csrf.disable());

        http.authorizeHttpRequests(authz -> authz.requestMatchers("/user/**").hasAuthority("ROLE_USER").requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN").anyRequest().permitAll());

        http.formLogin(form -> form.loginPage("/login.html").loginProcessingUrl("/login").permitAll().defaultSuccessUrl("/User-Dashboard.html").failureUrl("/login.html").usernameParameter("userEmail").passwordParameter("userPassword"));

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String rawPassword = authentication.getCredentials().toString();

                System.out.println("Catched Username and Password: "+ username + " : "+rawPassword);

                Client user = clientService.findByEmail(username);
                if (user == null || user.getUserName() == null) {
                    throw new BadCredentialsException("Invalid username or password");
                }

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (!encoder.matches(rawPassword, user.getUserPassword())) {
                    throw new BadCredentialsException("Invalid password");
                }

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
                return new UsernamePasswordAuthenticationToken(username, rawPassword, authorities);
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

}
