package com.FinBoard.User.Service.Service;

import com.FinBoard.User.Service.DTO.UserPrincipal;
import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Repositories.ClientRepository;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Log
public class UserDetailsServiceSecurity implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client =  clientRepository.findByUserEmail(username);
        if(client.isEmpty()){
            log.info("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(client.get());
    }
}
