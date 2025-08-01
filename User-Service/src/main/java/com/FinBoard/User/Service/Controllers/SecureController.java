package com.FinBoard.User.Service.Controllers;

import com.FinBoard.User.Service.DTO.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
public class SecureController {
    @GetMapping("/info")
    public ResponseEntity<?> getUserinfo(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Ritesh");

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
