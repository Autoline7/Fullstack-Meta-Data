package com.metaWebApp.MetaWebApp.repository;

import com.metaWebApp.MetaWebApp.model.AnalysisDataType;
import com.metaWebApp.MetaWebApp.model.AnalysisResult;
import com.metaWebApp.MetaWebApp.model.UserDataUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the AnalysisResult entity.
 * Provides CRUD operations and custom query methods for analysis results.
 */
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, UUID> {

    /**
     * Finds all AnalysisResults associated with a specific UserDataUpload.
     *
     * @param upload The UserDataUpload entity whose results are to be retrieved.
     * @return A list of AnalysisResults belonging to the upload.
     */
    List<AnalysisResult> findByUpload(UserDataUpload upload);

    /**
     * Finds all AnalysisResults by the ID of the associated UserDataUpload.
     *
     * @param uploadId The UUID of the UserDataUpload.
     * @return A list of AnalysisResults belonging to the upload.
     */
    List<AnalysisResult> findByUploadId(UUID uploadId);

    /**
     * Finds AnalysisResults by the associated UserDataUpload and a specific data type.
     *
     * @param upload The UserDataUpload entity.
     * @param dataType The AnalysisDataType to filter by.
     * @return A list of AnalysisResults matching the criteria.
     */
    List<AnalysisResult> findByUploadAndDataType(UserDataUpload upload, AnalysisDataType dataType);

    /**
     * Finds AnalysisResults by the Upload ID and a specific data type.
     *
     * @param uploadId The UUID of the UserDataUpload.
     * @param dataType The AnalysisDataType to filter by.
     * @return A list of AnalysisResults matching the criteria.
     */
    List<AnalysisResult> findByUploadIdAndDataType(UUID uploadId, AnalysisDataType dataType);

    /**
     * Finds AnalysisResults by the Upload ID, data type, and target identifier.
     *
     * @param uploadId The UUID of the UserDataUpload.
     * @param dataType The AnalysisDataType to filter by.
     * @param targetIdentifier The identifier of the target (e.g., username).
     * @return An Optional containing the AnalysisResult if found, or empty.
     */
    Optional<AnalysisResult> findByUploadIdAndDataTypeAndTargetIdentifier(UUID uploadId, AnalysisDataType dataType, String targetIdentifier);
}