package com.metaWebApp.MetaWebApp.contoller;

import com.metaWebApp.MetaWebApp.dto.PasswordResetConfirmDTO;
import com.metaWebApp.MetaWebApp.dto.PasswordResetRequestDTO;
import com.metaWebApp.MetaWebApp.model.PasswordReset;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.service.PasswordResetService;
import com.metaWebApp.MetaWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/password-resets")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetController(PasswordResetService passwordResetService,
                                   UserService userService,
                                   PasswordEncoder passwordEncoder) {
        this.passwordResetService = passwordResetService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint for a user to request a password reset token.
     * Accepts PasswordResetRequestDTO for input.
     *
     * @param request Contains the user's email address.
     * @return A generic success message.
     */
    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@Valid @RequestBody PasswordResetRequestDTO request) {
        Optional<User> userOptional = userService.getUserByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PasswordReset resetToken = passwordResetService.generateResetToken(user);
            // In a real application: Send this resetToken.getToken() to the user's email
            System.out.println("DEBUG: Password reset token generated for " + user.getEmail() + ": " + resetToken.getToken());
        }

        // Always return OK, regardless of whether the email was found, for security.
        return new ResponseEntity<>("If an account with that email exists, a password reset link has been sent.", HttpStatus.OK);
    }

    /**
     * Endpoint to validate a password reset token.
     * Returns a string status.
     *
     * @param token The password reset token from the URL.
     * @return ResponseEntity indicating whether the token is valid.
     */
    @GetMapping("/validate/{token}")
    public ResponseEntity<String> validateResetToken(@PathVariable String token) {
        Optional<PasswordReset> resetOptional = passwordResetService.validateResetToken(token);
        if (resetOptional.isPresent()) {
            return new ResponseEntity<>("Token is valid.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired token.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to perform the actual password reset.
     * Accepts PasswordResetConfirmDTO for input.
     *
     * @param request Contains the token and the new plain-text password.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetConfirmDTO request) {
        try {
            Optional<PasswordReset> resetOptional = passwordResetService.validateResetToken(request.getToken());

            if (resetOptional.isEmpty()) {
                return new ResponseEntity<>("Invalid or expired token.", HttpStatus.BAD_REQUEST);
            }

            PasswordReset passwordReset = resetOptional.get();
            User user = passwordReset.getUser();

            if (user == null) {
                return new ResponseEntity<>("User associated with token not found.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Hash the new password before saving
            String newHashedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPasswordHash(newHashedPassword);
            userService.updateUser(user); // Update the user's password

            passwordResetService.markTokenAsUsed(request.getToken());

            return new ResponseEntity<>("Password has been reset successfully.", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred during password reset.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}