package com.szoftverhazi.projektmanagement.services.auth;

import com.szoftverhazi.projektmanagement.dto.SignupRequest;
import com.szoftverhazi.projektmanagement.dto.UserDto;
import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.enums.UserRole;
import com.szoftverhazi.projektmanagement.reposities.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @PostConstruct
    public void createAdminAccount()
    {
        Optional<User>optionalUser=userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalUser.isEmpty())
        {
            User user= new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin created");
        }
        else {
            System.out.println("Admin already exists");
        }
    }

    @Override
    public UserDto signupUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

        String regex="^[a-zA-Z0-9._%+-]+@test\\.com$";
        Pattern pattern= Pattern.compile(regex);
        if (!pattern.matcher(signupRequest.getEmail()).matches())
        {
            user.setUserRole(UserRole.CUSTOMER);
        }
        else
        {
            user.setUserRole(UserRole.USER);
        }
        User createdUser=userRepository.save(user);

        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
