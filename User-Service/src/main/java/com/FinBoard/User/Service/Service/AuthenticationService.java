package com.FinBoard.User.Service.Service;

import com.FinBoard.User.Service.DTO.UserDTO;
import com.FinBoard.User.Service.DTO.UserPrincipal;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Log
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    public String validate(UserDTO userDTO){
        Authentication authentication=
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userDTO.getUserEmail(),userDTO.getUserPassword()
                        )
                );
        if(authentication.isAuthenticated()){
            UserPrincipal userPrincipal=(UserPrincipal) authentication.getPrincipal();
//            log.info("Authentication manager user principal "+userPrincipal.getPassword()+"  : "+userPrincipal.getUserEmail()+" : "+ userPrincipal.getUserName());
            return jwtService.generateToken(userPrincipal);
        }
        return "Failed";
    }
}
