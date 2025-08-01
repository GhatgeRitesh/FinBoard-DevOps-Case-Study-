package com.FinBoard.User.Service.Service;

import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private ClientService clientService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("Finding User in CustomeuserDetails Service");
    Client client = clientService.findByEmail(email);
    if (client == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }

    return new User(
            client.getUserEmail(),
            client.getUserPassword(),
            Collections.singleton(() -> client.getRole())
    );
  }

}
