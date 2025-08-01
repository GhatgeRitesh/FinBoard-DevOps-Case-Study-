package com.FinBoard.User.Service.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "client")
@Data
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    
    @Column(unique = true, nullable = false)
    private String userEmail;

    private Long userContact;

    private String userAddress;

    private String userPassword;

    private String profession;

    private String purpose;

    private String role;
}
