package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
