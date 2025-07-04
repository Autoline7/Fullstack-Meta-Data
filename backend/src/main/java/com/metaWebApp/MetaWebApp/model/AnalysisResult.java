package com.metaWebApp.MetaWebApp.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes; // Ensure this import is correct for your Hibernate version

/**
 * Stores granular results from various types of data analysis performed on a UserDataUpload.
 * This entity maps to the 'analysis_results' table.
 */
@Entity
@Table(name = "analysis_results")
public class AnalysisResult {

    /**
     * Unique identifier for each analysis result entry.
     * Generated automatically as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * The UserDataUpload that generated this analysis result.
     * This is a Many-to-One relationship, as many analysis results can come from one upload.
     * The 'upload_id' column in the database will store the UUID of the associated UserDataUpload.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
    @JoinColumn(name = "upload_id", nullable = false) // Foreign key column
    private UserDataUpload upload; // The associated UserDataUpload entity

    /**
     * Type of analysis result (e.g., 'UNFOLLOWER', 'CLOSE_FRIEND_ITEM', 'MESSAGE_THREAD_SUMMARY').
     * Stored as a String, but mapped from an enum for type safety in Java.
     */
    @Enumerated(EnumType.STRING) // Stores the enum name as a string in the DB
    @Column(name = "data_type", nullable = false, length = 100)
    private AnalysisDataType dataType;

    /**
     * Key identifier for the analyzed entity (e.g., Instagram username, Message thread ID).
     */
    @Column(name = "target_identifier", nullable = false, length = 255)
    private String targetIdentifier;

    /**
     * Optional numerical value associated with the result (e.g., count of messages in thread).
     * Using Long wrapper to allow null values.
     */
    @Column(name = "value_numeric")
    private Long valueNumeric;

    /**
     * Optional textual value (e.g., snippet of a message, comment text).
     * Mapped to TEXT type in PostgreSQL for potentially longer strings.
     */
    @Column(name = "value_text", columnDefinition = "TEXT")
    private String valueText;

    /**
     * Flexible JSON object for additional, unstructured, or varying metadata.
     * Mapped to JSONB type in PostgreSQL. Uses Hibernate 6's native JSON mapping.
     */
    @JdbcTypeCode(SqlTypes.JSON) // Tells Hibernate to use JSON type mapping
    @Column(name = "meta_json", columnDefinition = "JSONB") // Specifies JSONB column type for PostgreSQL
    private JsonNode metaJson; // Jackson's JsonNode for flexible JSON handling

    /**
     * Timestamp when this specific analysis result was recorded.
     * Automatically set on creation.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // --- Constructors ---
    public AnalysisResult() {
        // Default constructor required by JPA
    }

    // You might want a convenient constructor for creating results
    public AnalysisResult(UserDataUpload upload, AnalysisDataType dataType, String targetIdentifier) {
        this.upload = upload;
        this.dataType = dataType;
        this.targetIdentifier = targetIdentifier;
        this.createdAt = LocalDateTime.now(); // Set creation time
    }

    // --- Lifecycle Callbacks ---
    // Automatically set createdAt timestamp on entity persistence
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
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

    public UserDataUpload getUpload() {
        return upload;
    }

    public void setUpload(UserDataUpload upload) {
        this.upload = upload;
    }

    public AnalysisDataType getDataType() {
        return dataType;
    }

    public void setDataType(AnalysisDataType dataType) {
        this.dataType = dataType;
    }

    public String getTargetIdentifier() {
        return targetIdentifier;
    }

    public void setTargetIdentifier(String targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public Long getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(Long valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public JsonNode getMetaJson() {
        return metaJson;
    }

    public void setMetaJson(JsonNode metaJson) {
        this.metaJson = metaJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Omitted setter for createdAt to enforce @PrePersist


    @Override
    public String toString() {
        return "AnalysisResult{" +
                "id=" + id +
                ", uploadId=" + (upload != null ? upload.getId() : "null") +
                ", dataType=" + dataType +
                ", targetIdentifier='" + targetIdentifier + '\'' +
                ", valueNumeric=" + valueNumeric +
                ", valueText='" + valueText + '\'' +
                ", metaJson=" + (metaJson != null ? metaJson.toString() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
