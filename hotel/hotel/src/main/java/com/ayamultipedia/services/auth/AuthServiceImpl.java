package com.ayamultipedia.services.auth;


import com.ayamultipedia.dto.SignupRequest;
import com.ayamultipedia.dto.UserDto;
import com.ayamultipedia.entity.User;
import com.ayamultipedia.enums.UserRole;
import com.ayamultipedia.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;


    // THE CREATION OF ADMIN ACCOUNT
    @PostConstruct
    public void createAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setUserRole(UserRole.ADMIN);
            //user.setPassword("admin");
            // Hash the password
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exists");
        }
    }


    // ON CREATION USER ACCOUNT
    public UserDto createUser(SignupRequest signupRequest) {
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists" + signupRequest.getEmail());
        }
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }
}
