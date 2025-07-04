package com.metaWebApp.MetaWebApp.service;

import com.metaWebApp.MetaWebApp.model.AnalysisDataType;
import com.metaWebApp.MetaWebApp.model.AnalysisResult;
import com.metaWebApp.MetaWebApp.repository.AnalysisResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnalysisResultService {

    private final AnalysisResultRepository analysisResultRepository;

    @Autowired
    public AnalysisResultService(AnalysisResultRepository analysisResultRepository) {
        this.analysisResultRepository = analysisResultRepository;
    }

    @Transactional
    public AnalysisResult saveAnalysisResult(AnalysisResult analysisResult) {
        return analysisResultRepository.save(analysisResult);
    }

    @Transactional(readOnly = true)
    public Optional<AnalysisResult> getAnalysisResultById(UUID id) {
        return analysisResultRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<AnalysisResult> getResultsForUpload(UUID uploadId) {
        return analysisResultRepository.findByUploadId(uploadId);
    }

    @Transactional(readOnly = true)
    public List<AnalysisResult> getResultsForUploadAndType(UUID uploadId, AnalysisDataType dataType) {
        return analysisResultRepository.findByUploadIdAndDataType(uploadId, dataType);
    }

    // You will add more complex business logic here as you develop,
    // for example, methods that process raw JSON into AnalysisResults.
}
