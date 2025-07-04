package com.metaWebApp.MetaWebApp.dto;


import com.metaWebApp.MetaWebApp.model.UploadStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for updating the status of a UserDataUpload record.
 */
public class UploadStatusUpdateDTO {

    @NotNull(message = "New status cannot be null")
    private UploadStatus newStatus;

    private String errorMessage; // Optional: for FAILED or INVALID_FILE statuses

    // Getters and Setters
    public UploadStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(UploadStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
