package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.file.File;
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
    private Long id;
    private String report;
    private String title;
    private String conclusion;
    private LocalDateTime createdOn;
    private FileResponse fileResponse;

    public DiagnosticResponse toDiagnosticResponse(Diagnostic diagnosticInput){
        Diagnostic diagnostic = diagnosticInput;
        FileResponse fileValue = diagnostic.getFile() == null ? null: new FileResponse().toFileResponse(diagnostic.getFile());

        return DiagnosticResponse.builder()
                .id(diagnostic.getId())
                .createdOn(diagnostic.getCreatedOn())
                .report(diagnostic.getReport())
                .conclusion(diagnostic.getConclusion())
                .fileResponse(fileValue)
                .title(diagnostic.getTitle())
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