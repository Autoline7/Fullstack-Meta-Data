package com.metaWebApp.MetaWebApp.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.metaWebApp.MetaWebApp.model.AnalysisDataType;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning AnalysisResult details.
 * The metaJson field is returned as a generic JsonNode, allowing frontend
 * to interpret it based on `dataType`.
 */
public class AnalysisResultResponseDTO {

    private UUID id;
    private UUID uploadId; // Expose upload ID, not the whole UserDataUpload object
    private AnalysisDataType dataType;
    private String targetIdentifier;
    private Long valueNumeric;
    private String valueText;
    private JsonNode metaJson; // Still JsonNode, as its structure varies by dataType
    private LocalDateTime createdAt;

    // Default constructor for Jackson
    public AnalysisResultResponseDTO() {
    }

    // Constructor to easily convert from AnalysisResult entity
    public AnalysisResultResponseDTO(UUID id, UUID uploadId, AnalysisDataType dataType,
                                     String targetIdentifier, Long valueNumeric, String valueText,
                                     JsonNode metaJson, LocalDateTime createdAt) {
        this.id = id;
        this.uploadId = uploadId;
        this.dataType = dataType;
        this.targetIdentifier = targetIdentifier;
        this.valueNumeric = valueNumeric;
        this.valueText = valueText;
        this.metaJson = metaJson;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUploadId() { return uploadId; }
    public void setUploadId(UUID uploadId) { this.uploadId = uploadId; }
    public AnalysisDataType getDataType() { return dataType; }
    public void setDataType(AnalysisDataType dataType) { this.dataType = dataType; }
    public String getTargetIdentifier() { return targetIdentifier; }
    public void setTargetIdentifier(String targetIdentifier) { this.targetIdentifier = targetIdentifier; }
    public Long getValueNumeric() { return valueNumeric; }
    public void setValueNumeric(Long valueNumeric) { this.valueNumeric = valueNumeric; }
    public String getValueText() { return valueText; }
    public void setValueText(String valueText) { this.valueText = valueText; }
    public JsonNode getMetaJson() { return metaJson; }
    public void setMetaJson(JsonNode metaJson) { this.metaJson = metaJson; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
