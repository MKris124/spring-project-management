package com.szoftverhazi.projektmanagement.controller.admin;

import com.szoftverhazi.projektmanagement.dto.UserDto;
import com.szoftverhazi.projektmanagement.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees()
    {
        return ResponseEntity.ok(adminService.getEmployees());
    }
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers()
    {
        return ResponseEntity.ok(adminService.getCustomers());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<UserDto> updateUserRole(
            @PathVariable Long userId,
            @RequestParam String newRole) {
        UserDto updatedUser = adminService.updateUserRole(userId, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    // Felhasználói jelszó visszaállítása
    @PutMapping("/users/{userId}/reset-password")
    public ResponseEntity<Void> resetPassword(
            @PathVariable Long userId,
            @RequestParam String newPassword) {
        adminService.resetPassword(userId, newPassword);
        return ResponseEntity.noContent().build();
    }
}
