package com.szoftverhazi.projektmanagement.services.admin;

import com.szoftverhazi.projektmanagement.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getCustomers();
    List<UserDto> getEmployees();
    public void deleteUser(Long userId);
    public UserDto updateUserRole(Long userId, String newRole);
    public void resetPassword(Long userId, String newPassword);
}
