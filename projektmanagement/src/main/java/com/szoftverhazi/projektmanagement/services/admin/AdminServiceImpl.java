package com.szoftverhazi.projektmanagement.services.admin;

import com.szoftverhazi.projektmanagement.dto.UserDto;
import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.enums.UserRole;
import com.szoftverhazi.projektmanagement.reposities.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;


    @Override
    public List<UserDto> getCustomers() {
        return userRepository.findAll()
                .stream()
                .filter(employee->employee.getUserRole()== UserRole.CUSTOMER)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(employee->employee.getUserRole()== UserRole.PROJECT_MANAGER|| employee.getUserRole()==UserRole.USER)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find User by ID: " + userId));

        userRepository.delete(user);
    }

    @Override
    public UserDto updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find User by ID: " + userId));

        if (user.getUserRole() == UserRole.USER || user.getUserRole() == UserRole.PROJECT_MANAGER) {
            try {
                UserRole role = UserRole.valueOf(newRole);
                user.setUserRole(role);
                user = userRepository.save(user);
                return user.getUserDto();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unrecognised role: " + newRole);
            }
        } else {
            throw new IllegalArgumentException("Only the USER and PROJECT_MANAGER roles can be modified");
        }
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find User by ID: " + userId));

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
    }
}
