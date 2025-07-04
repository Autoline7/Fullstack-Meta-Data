package com.metaWebApp.MetaWebApp.service;

import com.metaWebApp.MetaWebApp.model.DeclaredFileType;
import com.metaWebApp.MetaWebApp.model.UploadStatus;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.model.UserDataUpload;
import com.metaWebApp.MetaWebApp.repository.UserDataUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing UserDataUpload entities.
 * Handles business logic related to user data uploads and their analysis status.
 */
@Service
public class UserDataUploadService {

    private final UserDataUploadRepository userDataUploadRepository;

    @Autowired
    public UserDataUploadService(UserDataUploadRepository userDataUploadRepository) {
        this.userDataUploadRepository = userDataUploadRepository;
    }

    /**
     * Creates and saves a new UserDataUpload record.
     *
     * @param user The user performing the upload.
     * @param fileName The original name of the uploaded file.
     * @param filePath The storage path of the file (e.g., S3 URL).
     * @param declaredFileType The type of file as declared by the user.
     * @return The newly created UserDataUpload entity.
     */
    @Transactional
    public UserDataUpload createUploadRecord(User user, String fileName, String filePath, DeclaredFileType declaredFileType) {
        UserDataUpload upload = new UserDataUpload(user, fileName, filePath, declaredFileType);
        // Status and uploadTime are set by constructor and @PrePersist
        return userDataUploadRepository.save(upload);
    }

    /**
     * Retrieves a UserDataUpload record by its ID.
     *
     * @param id The UUID of the upload record.
     * @return An Optional containing the UserDataUpload if found, or empty.
     */
    @Transactional(readOnly = true)
    public Optional<UserDataUpload> getUploadRecordById(UUID id) {
        return userDataUploadRepository.findById(id);
    }

    /**
     * Retrieves all UserDataUpload records for a specific user.
     *
     * @param userId The UUID of the user.
     * @return A list of UserDataUploads belonging to the user.
     */
    @Transactional(readOnly = true)
    public List<UserDataUpload> getUploadRecordsByUserId(UUID userId) {
        return userDataUploadRepository.findByUserId(userId);
    }

    /**
     * Updates the status of a UserDataUpload record.
     *
     * @param uploadId The UUID of the upload record to update.
     * @param newStatus The new status to set.
     * @param errorMessage Optional error message if the status is FAILED or INVALID_FILE.
     * @return The updated UserDataUpload entity.
     * @throws IllegalArgumentException if the upload record is not found.
     */
    @Transactional
    public UserDataUpload updateUploadStatus(UUID uploadId, UploadStatus newStatus, String errorMessage) {
        UserDataUpload upload = userDataUploadRepository.findById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("Upload record not found with ID: " + uploadId));

        upload.setStatus(newStatus);
        upload.setErrorMessage(errorMessage); // Set error message, will be null if no error

        return userDataUploadRepository.save(upload);
    }

    /**
     * Updates summary counts for a UserDataUpload record after analysis.
     *
     * @param uploadId The UUID of the upload record.
     * @param totalFollowers Total followers count.
     * @param totalFollowing Total following count.
     * @param unfollowersCount Count of unfollowers.
     * @param totalCloseFriends Total close friends count.
     * @return The updated UserDataUpload entity.
     * @throws IllegalArgumentException if the upload record is not found.
     */
    @Transactional
    public UserDataUpload updateAnalysisSummaries(UUID uploadId,
                                                  Integer totalFollowers, Integer totalFollowing, Integer unfollowersCount,
                                                  Integer totalCloseFriends) {
        UserDataUpload upload = userDataUploadRepository.findById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("Upload record not found with ID: " + uploadId));

        upload.setTotalFollowers(totalFollowers);
        upload.setTotalFollowing(totalFollowing);
        upload.setUnfollowersCount(unfollowersCount);
        upload.setTotalCloseFriends(totalCloseFriends);

        return userDataUploadRepository.save(upload);
    }

    /**
     * Deletes a UserDataUpload record by its ID.
     *
     * @param id The UUID of the upload record to delete.
     */
    @Transactional
    public void deleteUploadRecord(UUID id) {
        userDataUploadRepository.deleteById(id);
    }
}
