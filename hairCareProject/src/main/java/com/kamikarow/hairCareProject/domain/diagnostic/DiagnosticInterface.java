package com.kamikarow.hairCareProject.domain.diagnostic;

import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticRequest;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;

import java.util.List;

public interface DiagnosticInterface {


    public Diagnostic save(DiagnosticRequest diagnosticRequest, String token);
    public List<DiagnosticResponse> retrieveAllDiagnostics(String token);
}
