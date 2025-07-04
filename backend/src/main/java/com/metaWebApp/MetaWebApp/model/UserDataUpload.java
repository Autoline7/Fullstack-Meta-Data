package com.metaWebApp.MetaWebApp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_data_uploads")
public class UserDataUpload {

    /**
     * Unique identifier for each data upload.
     * Generated automatically as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generates a UUID for the primary key
    @Column(name = "id", updatable = false, nullable = false) // Ensures ID is not updatable and not null
    private UUID id;

    /**
     * The user who performed this upload.
     * This is a Many-to-One relationship, as many uploads can belong to one user.
     * The 'user_id' column in the database will store the UUID of the associated User.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching to avoid loading User unless explicitly needed
    @JoinColumn(name = "user_id", nullable = false) // Specifies the foreign key column name
    private User user; // The associated User entity

    /**
     * Timestamp when the data was uploaded.
     * Automatically set on creation.
     */
    @Column(name = "upload_time", nullable = false, updatable = false)
    private LocalDateTime uploadTime;

    /**
     * The original name of the uploaded file (e.g., "instagram_data.zip", "close_friends.json").
     */
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    /**
     * The path or identifier to the stored file in object storage (e.g., S3 URL).
     */
    @Column(name = "file_path", nullable = false, length = 1024)
    private String filePath;

    /**
     * The type of file declared by the frontend (e.g., 'followers', 'close_friends', 'messages').
     * Stored as a String, but mapped from an enum for type safety in Java.
     */
    @Enumerated(EnumType.STRING) // Stores the enum name (e.g., "FOLLOWERS") as a string in the DB
    @Column(name = "declared_file_type", nullable = false, length = 50)
    private DeclaredFileType declaredFileType;

    /**
     * The current status of the upload and analysis process.
     * Stored as a String, but mapped from an enum for type safety in Java.
     */
    @Enumerated(EnumType.STRING) // Stores the enum name (e.g., "PENDING") as a string in the DB
    @Column(name = "status", nullable = false, length = 50)
    private UploadStatus status;

    /**
     * Detailed error message if the upload or analysis failed.
     * Can be null if no error occurred.
     */
    @Column(name = "error_message", columnDefinition = "TEXT") // Maps to TEXT type in PostgreSQL
    private String errorMessage;

    // --- Summary Fields from Analysis (can be null if not applicable or not yet completed) ---

    @Column(name = "total_followers")
    private Integer totalFollowers; // Using Integer to allow null values

    @Column(name = "total_following")
    private Integer totalFollowing;

    @Column(name = "unfollowers_count")
    private Integer unfollowersCount;

    @Column(name = "total_close_friends")
    private Integer totalCloseFriends;



    // --- Constructors ---
    public UserDataUpload() {
        // Default constructor required by JPA
    }

    // You can add a convenient constructor for initial creation if needed
    public UserDataUpload(User user, String fileName, String filePath, DeclaredFileType declaredFileType) {
        this.user = user;
        this.fileName = fileName;
        this.filePath = filePath;
        this.declaredFileType = declaredFileType;
        this.status = UploadStatus.PENDING; // Default status
        this.uploadTime = LocalDateTime.now(); // Set creation time
    }

    // --- Lifecycle Callbacks ---
    // Automatically set uploadTime on entity persistence
    @PrePersist
    protected void onCreate() {
        if (uploadTime == null) {
            uploadTime = LocalDateTime.now();
        }
        if (status == null) {
            status = UploadStatus.PENDING; // Ensure default status if not set by constructor
        }
    }


    // --- Getters and Setters ---
    // (Generate these or write manually)

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    // Typically, you don't provide a public setter for auto-generated timestamps like uploadTime
    // But if needed for testing or specific scenarios, you can add it.
    // For this example, I'll omit it to enforce @PrePersist.

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

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(Integer totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public Integer getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(Integer totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public Integer getUnfollowersCount() {
        return unfollowersCount;
    }

    public void setUnfollowersCount(Integer unfollowersCount) {
        this.unfollowersCount = unfollowersCount;
    }

    public Integer getTotalCloseFriends() {
        return totalCloseFriends;
    }

    public void setTotalCloseFriends(Integer totalCloseFriends) {
        this.totalCloseFriends = totalCloseFriends;
    }



    @Override
    public String toString() {
        return "UserDataUpload{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : "null") + // Avoid fetching user for toString
                ", uploadTime=" + uploadTime +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", declaredFileType=" + declaredFileType +
                ", status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                ", totalFollowers=" + totalFollowers +
                ", totalFollowing=" + totalFollowing +
                ", unfollowersCount=" + unfollowersCount +
                ", totalCloseFriends=" + totalCloseFriends +
                '}';
    }

}
