package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiagnosticDao {

    private final DiagnosticJpaRepository diagnosticJpaRepository;

    public Diagnostic save(Diagnostic diagnostic){
        return this.diagnosticJpaRepository.save(diagnostic);
    }

    public List<Optional<Diagnostic>> retrievesAll(Long id){
        return this.diagnosticJpaRepository.findAllDiagnosticsBy(id);
    }
}
