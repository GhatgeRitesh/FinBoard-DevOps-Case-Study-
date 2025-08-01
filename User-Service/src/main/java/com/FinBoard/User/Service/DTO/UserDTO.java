package com.FinBoard.User.Service.DTO;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class UserDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private Long userContact;
    private String userAddress;
    private String userPassword;
    private String profession;
    private String purpose;
    private String role;
}
