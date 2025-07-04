package com.metaWebApp.MetaWebApp.dto;



import com.metaWebApp.MetaWebApp.model.DeclaredFileType;
import com.metaWebApp.MetaWebApp.model.UploadStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning UserDataUpload details.
 */
public class UploadResponseDTO {

    private UUID id;
    private UUID userId; // Expose user ID, not the whole User object
    private String fileName;
    private String filePath;
    private DeclaredFileType declaredFileType;
    private UploadStatus status;
    private String errorMessage;
    private Integer totalFollowers;
    private Integer totalFollowing;
    private Integer unfollowersCount;
    private Integer totalCloseFriends;
    private LocalDateTime uploadTime;

    // Default constructor for Jackson
    public UploadResponseDTO() {
    }

    // Constructor to easily convert from UserDataUpload entity
    public UploadResponseDTO(UUID id, UUID userId, String fileName, String filePath,
                             DeclaredFileType declaredFileType, UploadStatus status, String errorMessage,
                             Integer totalFollowers, Integer totalFollowing, Integer unfollowersCount,
                             Integer totalCloseFriends,
                             LocalDateTime uploadTime) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.declaredFileType = declaredFileType;
        this.status = status;
        this.errorMessage = errorMessage;
        this.totalFollowers = totalFollowers;
        this.totalFollowing = totalFollowing;
        this.unfollowersCount = unfollowersCount;
        this.totalCloseFriends = totalCloseFriends;
        this.uploadTime = uploadTime;
    }


    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public DeclaredFileType getDeclaredFileType() { return declaredFileType; }
    public void setDeclaredFileType(DeclaredFileType declaredFileType) { this.declaredFileType = declaredFileType; }
    public UploadStatus getStatus() { return status; }
    public void setStatus(UploadStatus status) { this.status = status; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public Integer getTotalFollowers() { return totalFollowers; }
    public void setTotalFollowers(Integer totalFollowers) { this.totalFollowers = totalFollowers; }
    public Integer getTotalFollowing() { return totalFollowing; }
    public void setTotalFollowing(Integer totalFollowing) { this.totalFollowing = totalFollowing; }
    public Integer getUnfollowersCount() { return unfollowersCount; }
    public void setUnfollowersCount(Integer unfollowersCount) { this.unfollowersCount = unfollowersCount; }
    public Integer getTotalCloseFriends() { return totalCloseFriends; }
    public void setTotalCloseFriends(Integer totalCloseFriends) { this.totalCloseFriends = totalCloseFriends; }
    public LocalDateTime getUploadTime() { return uploadTime; }
    public void setUploadTime(LocalDateTime uploadTime) { this.uploadTime = uploadTime; }
}
