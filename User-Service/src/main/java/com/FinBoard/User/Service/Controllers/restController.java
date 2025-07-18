package com.FinBoard.User.Service.Controllers;

import com.FinBoard.User.Service.DTO.UserDTO;
import com.FinBoard.User.Service.Entities.Client;
import com.FinBoard.User.Service.Service.ClientService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.Map;

@RestController
@Log
@RequestMapping("/userservice")
public class restController{

    @Autowired
    private ClientService clientService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        if(userDTO == null) throw new RuntimeException("No Content Recieved" + HttpStatus.NO_CONTENT);
        try{
            MessageDigest digest= MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(userDTO.getUserPassword().getBytes());

            StringBuilder hashedPassword= new StringBuilder();
            for(byte b: encodedHash){
                hashedPassword.append(String.format("%02x", b));
            }

            Client cli = new Client();
            cli.setUserPassword(hashedPassword.toString());
            cli.setUserName(userDTO.getUserName());
            cli.setUserEmail(userDTO.getUserEmail());
            cli.setUserContact(userDTO.getUserContact());
            cli.setUserAddress(userDTO.getUserAddress());
            cli.setProfession(userDTO.getProfession());
            cli.setPurpose(userDTO.getPurpose());
            clientService.registerClient(cli);
            return new ResponseEntity<>(Map.of("message","User Registered Successfully"),HttpStatus.OK);
        }catch (Exception e){
            log.info("Exception in controller "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
