package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userID;
    private UserRole userRole;
}
