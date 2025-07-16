package com.FinBoard.User.Service.Service;

import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Repositories.ClientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
