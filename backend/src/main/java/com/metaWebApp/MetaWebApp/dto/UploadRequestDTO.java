package com.metaWebApp.MetaWebApp.dto;

import com.metaWebApp.MetaWebApp.model.DeclaredFileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * DTO for requesting a new UserDataUpload record.
 * This DTO captures metadata about the file to be uploaded.
 */
public class UploadRequestDTO {

    // Assuming the userId will be obtained from authentication context,
    // but included here for direct testing if auth isn't fully set up yet.
    // In a real app, you would NOT pass userId from frontend for authenticated actions.
    private UUID userId;

    @NotBlank(message = "File name cannot be empty")
    private String fileName;

    @NotBlank(message = "File path/URL cannot be empty")
    private String filePath; // This would typically be a temporary URL or cloud storage path

    @NotNull(message = "Declared file type cannot be empty")
    private DeclaredFileType declaredFileType;

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DeclaredFileType getDeclaredFileType() {
        return declaredFileType;
    }

    public void setDeclaredFileType(DeclaredFileType declaredFileType) {
        this.declaredFileType = declaredFileType;
    }
}