package com.metaWebApp.MetaWebApp.model;

/**
 * Represents the current status of a data upload and its analysis.
 */
public enum UploadStatus {
    PENDING,        // Upload received, waiting for processing
    PROCESSING,     // Analysis is currently underway
    COMPLETED,      // Analysis finished successfully
    FAILED,         // Analysis failed due to an internal error
    INVALID_FILE    // File was uploaded but deemed invalid (e.g., wrong format, corrupted)
}
