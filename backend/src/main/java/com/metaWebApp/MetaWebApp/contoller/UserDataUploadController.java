package com.metaWebApp.MetaWebApp.contoller;

import com.metaWebApp.MetaWebApp.dto.UploadRequestDTO;
import com.metaWebApp.MetaWebApp.dto.UploadResponseDTO;
import com.metaWebApp.MetaWebApp.dto.UploadStatusUpdateDTO;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.model.UserDataUpload;
import com.metaWebApp.MetaWebApp.service.UserDataUploadService;
import com.metaWebApp.MetaWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // For DTO validation

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors; // For stream operations

@RestController
@RequestMapping("/api/uploads")
public class UserDataUploadController {

    private final UserDataUploadService userDataUploadService;
    private final UserService userService;

    @Autowired
    public UserDataUploadController(UserDataUploadService userDataUploadService, UserService userService) {
        this.userDataUploadService = userDataUploadService;
        this.userService = userService;
    }

    /**
     * Helper method to convert UserDataUpload entity to UploadResponseDTO.
     * In a larger application, consider using MapStruct or ModelMapper.
     */
    private UploadResponseDTO convertToDto(UserDataUpload upload) {
        if (upload == null) {
            return null;
        }
        return new UploadResponseDTO(
                upload.getId(),
                upload.getUser() != null ? upload.getUser().getId() : null, // Get userId from associated User
                upload.getFileName(),
                upload.getFilePath(),
                upload.getDeclaredFileType(),
                upload.getStatus(),
                upload.getErrorMessage(),
                upload.getTotalFollowers(),
                upload.getTotalFollowing(),
                upload.getUnfollowersCount(),
                upload.getTotalCloseFriends(),
                upload.getUploadTime()
        );
    }

    /**
     * Endpoint to create a new user data upload record.
     * Accepts UploadRequestDTO for input.
     *
     * @param request The DTO containing upload details.
     * @return ResponseEntity with the created UploadResponseDTO or an error.
     */
    @PostMapping
    public ResponseEntity<UploadResponseDTO> createUpload(@Valid @RequestBody UploadRequestDTO request) {
        try {
            // SECURITY NOTE: In a real app, userId should come from authenticated context, NOT request body.
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            UserDataUpload newUpload = userDataUploadService.createUploadRecord(
                    user,
                    request.getFileName(),
                    request.getFilePath(),
                    request.getDeclaredFileType()
            );
            return new ResponseEntity<>(convertToDto(newUpload), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or specific error DTO
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a specific user data upload record by its ID.
     * Returns UploadResponseDTO.
     *
     * @param id The UUID of the upload record.
     * @return ResponseEntity with the UploadResponseDTO or NOT_FOUND status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UploadResponseDTO> getUploadById(@PathVariable UUID id) {
        return userDataUploadService.getUploadRecordById(id)
                .map(this::convertToDto) // Use helper to convert
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all upload records for a specific user.
     * Returns a list of UploadResponseDTOs.
     *
     * @param userId The UUID of the user.
     * @return ResponseEntity with a list of UploadResponseDTOs.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UploadResponseDTO>> getUploadsByUserId(@PathVariable UUID userId) {
        // SECURITY NOTE: In a real app, ensure this userId matches the authenticated user, or is an admin request.
        List<UserDataUpload> uploads = userDataUploadService.getUploadRecordsByUserId(userId);
        List<UploadResponseDTO> uploadDTOs = uploads.stream()
                .map(this::convertToDto) // Convert each entity to DTO
                .collect(Collectors.toList());
        return new ResponseEntity<>(uploadDTOs, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of an upload record.
     * Accepts UploadStatusUpdateDTO and returns UploadResponseDTO.
     *
     * @param id The UUID of the upload record to update.
     * @param request The DTO containing the new status and optional error message.
     * @return ResponseEntity with the updated UploadResponseDTO or an error status.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<UploadResponseDTO> updateUploadStatus(@PathVariable UUID id, @Valid @RequestBody UploadStatusUpdateDTO request) {
        try {
            UserDataUpload updatedUpload = userDataUploadService.updateUploadStatus(
                    id, request.getNewStatus(), request.getErrorMessage()
            );
            return new ResponseEntity<>(convertToDto(updatedUpload), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUpload(@PathVariable UUID id) {
        try {
            userDataUploadService.deleteUploadRecord(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}