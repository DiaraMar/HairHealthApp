package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticResponse {
    private String report;
    private String conclusion;
    private LocalDateTime createdOn;
    private User createdBy;

    public DiagnosticResponse toDiagnosticResponse(Diagnostic diagnosticInput){
        Diagnostic diagnostic = diagnosticInput;
        return DiagnosticResponse.builder()
                .createdOn(diagnostic.getCreatedOn())
                .report(diagnostic.getReport())
                .conclusion(diagnostic.getConclusion())
                .build();
    }

    public List<DiagnosticResponse> toListOfDiagnosticResponse(List <Diagnostic>diagnosticList){
        List<DiagnosticResponse> diagnosticResponseList = new ArrayList<>();
        for (Diagnostic diagnostic : diagnosticList) {
            diagnosticResponseList.add(toDiagnosticResponse(diagnostic));
        }
        return diagnosticResponseList;
    }


}