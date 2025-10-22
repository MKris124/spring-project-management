package com.szoftverhazi.projektmanagement.controller.auth;

import com.szoftverhazi.projektmanagement.dto.AuthenticationRequest;
import com.szoftverhazi.projektmanagement.dto.AuthenticationResponse;
import com.szoftverhazi.projektmanagement.dto.SignupRequest;
import com.szoftverhazi.projektmanagement.dto.UserDto;
import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.reposities.UserRepository;
import com.szoftverhazi.projektmanagement.services.auth.AuthService;
import com.szoftverhazi.projektmanagement.services.jwt.UserService;
import com.szoftverhazi.projektmanagement.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthController {
    public final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest)
    {
        if (authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        UserDto createdUserDto =authService.signupUser(signupRequest);
        if (createdUserDto==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest)
    {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken((
                    authenticationRequest.getEmail()),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e) {
            throw  new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails= userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser=userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse= new AuthenticationResponse();
        if (optionalUser.isPresent())
        {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserID(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }
}
