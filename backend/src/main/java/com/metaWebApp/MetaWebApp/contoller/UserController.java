package com.metaWebApp.MetaWebApp.contoller;

import com.metaWebApp.MetaWebApp.dto.PasswordChangeDTO;
import com.metaWebApp.MetaWebApp.dto.UserResponseDTO;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController // Marks this class as a REST Controller
@RequestMapping("/api/users") // Base path for all endpoints in this controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping // Handles POST requests to /api/users
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody User user) {
        try {
            // In a real app, you'd use a DTO for input and hash the password here or in service
            // For simple testing, we're directly using the User entity as request body (NOT recommended for production)
            User createdUser = userService.createUser(user);
            UserResponseDTO userResponseDTO = new UserResponseDTO(createdUser.getId(),createdUser.getEmail(),createdUser.getUpdatedAt(), createdUser.getUpdatedAt(),createdUser.getIsPremium());
            return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Or return a custom error DTO
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}") // Handles GET requests to /api/users/{id}
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(user -> new UserResponseDTO( // Convert entity to DTO before returning
                        user.getId(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.getIsPremium()
                ))
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // You can add more endpoints (GET all, PUT for update, DELETE)
}