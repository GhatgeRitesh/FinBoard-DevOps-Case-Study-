package com.FinBoard.User.Service.Service;

import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Repositories.ClientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

   public Boolean registerClient(Client clientBody){
       try{clientRepository.save(clientBody); return true;}catch(Exception e){
           log.info("Exception Occurred while registering client: "+ e.getMessage());
           return false;
       }
   }

   public Client findByEmail(String email){
         try{log.info("Finding User for email"+ email); Optional<Client> client=clientRepository.findByUserEmail(email); log.debug("client result"+ client.get().toString());return client.get();}catch (Exception e){
             log.info("Exception encountered : "+ e.getMessage());
             return null;
         }
   }
}
