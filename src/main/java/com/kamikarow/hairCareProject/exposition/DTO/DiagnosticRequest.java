package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.file.File;
import com.kamikarow.hairCareProject.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticRequest {
    @NotBlank
    private String client;
    @NotBlank
    private String report;
    @NotBlank
    private String conclusion;
    @NotBlank
    private String title;
    private File file;

    public Diagnostic toDiagnostic(){
        return Diagnostic.builder()
                .createdOn(LocalDateTime.now())
                .report(report)
                .conclusion(conclusion)
                .file(file)
                .title(title)
                .build();

    }

}