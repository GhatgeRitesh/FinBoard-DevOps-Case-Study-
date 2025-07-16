package com.FinBoard.User.Service.Controllers;

import com.FinBoard.User.Service.DTO.UserDTO;
import lombok.extern.java.Log;
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

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        if(userDTO == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(Map.of("message","User Registered Successfully"),HttpStatus.OK);
    }
}
