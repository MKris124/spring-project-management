package com.szoftverhazi.projektmanagement.reposities;

import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String username);
    Optional<User> findByUserRole(UserRole role);
}
