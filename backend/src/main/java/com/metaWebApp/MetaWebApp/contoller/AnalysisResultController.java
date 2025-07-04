package com.metaWebApp.MetaWebApp.contoller;

import com.metaWebApp.MetaWebApp.dto.AnalysisResultResponseDTO;
import com.metaWebApp.MetaWebApp.model.AnalysisDataType;
import com.metaWebApp.MetaWebApp.model.AnalysisResult;
import com.metaWebApp.MetaWebApp.service.AnalysisResultService;
import com.metaWebApp.MetaWebApp.service.UserDataUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analysis-results")
public class AnalysisResultController {

    private final AnalysisResultService analysisResultService;
    private final UserDataUploadService userDataUploadService;

    @Autowired
    public AnalysisResultController(AnalysisResultService analysisResultService, UserDataUploadService userDataUploadService) {
        this.analysisResultService = analysisResultService;
        this.userDataUploadService = userDataUploadService;
    }

    /**
     * Helper method to convert AnalysisResult entity to AnalysisResultResponseDTO.
     */
    private AnalysisResultResponseDTO convertToDto(AnalysisResult result) {
        if (result == null) {
            return null;
        }
        return new AnalysisResultResponseDTO(
                result.getId(),
                result.getUpload() != null ? result.getUpload().getId() : null, // Get uploadId
                result.getDataType(),
                result.getTargetIdentifier(),
                result.getValueNumeric(),
                result.getValueText(),
                result.getMetaJson(),
                result.getCreatedAt()
        );
    }

    /**
     * Retrieves a specific analysis result by its ID.
     * Returns AnalysisResultResponseDTO.
     *
     * @param id The UUID of the analysis result.
     * @return ResponseEntity with the AnalysisResultResponseDTO or NOT_FOUND status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResultResponseDTO> getResultById(@PathVariable UUID id) {
        return analysisResultService.getAnalysisResultById(id)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all analysis results associated with a specific user data upload.
     * Returns a list of AnalysisResultResponseDTOs.
     *
     * @param uploadId The UUID of the UserDataUpload record.
     * @return ResponseEntity with a list of AnalysisResultResponseDTOs.
     */
    @GetMapping("/upload/{uploadId}")
    public ResponseEntity<List<AnalysisResultResponseDTO>> getResultsForUpload(@PathVariable UUID uploadId) {
        // Optional: Validate if the uploadId exists or belongs to the authenticated user
        // (This check could also be pushed down into the service layer for consistency)
        userDataUploadService.getUploadRecordById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("Upload not found with ID: " + uploadId));

        List<AnalysisResult> results = analysisResultService.getResultsForUpload(uploadId);
        List<AnalysisResultResponseDTO> resultDTOs = results.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves analysis results for a specific user data upload and a given data type.
     * Returns a list of AnalysisResultResponseDTOs.
     *
     * @param uploadId The UUID of the UserDataUpload record.
     * @param dataType The type of analysis data.
     * @return ResponseEntity with a list of AnalysisResultResponseDTOs.
     */
    @GetMapping("/upload/{uploadId}/type/{dataType}")
    public ResponseEntity<List<AnalysisResultResponseDTO>> getResultsForUploadAndType(
            @PathVariable UUID uploadId,
            @PathVariable AnalysisDataType dataType) {
        // Optional: Validate uploadId
        userDataUploadService.getUploadRecordById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("Upload not found with ID: " + uploadId));

        List<AnalysisResult> results = analysisResultService.getResultsForUploadAndType(uploadId, dataType);
        List<AnalysisResultResponseDTO> resultDTOs = results.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
    }
}