package com.szoftverhazi.projektmanagement.services.auth;

import com.szoftverhazi.projektmanagement.dto.SignupRequest;
import com.szoftverhazi.projektmanagement.dto.UserDto;

public interface AuthService {
   UserDto signupUser(SignupRequest signupRequest);
   boolean hasUserWithEmail(String email);
}
