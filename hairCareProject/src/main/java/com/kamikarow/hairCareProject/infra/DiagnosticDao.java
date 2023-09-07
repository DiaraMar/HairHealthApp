package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DiagnosticDao {

    private final DiagnosticJpaRepository diagnosticJpaRepository;

    public Diagnostic save(Diagnostic diagnostic){
        System.out.println(diagnostic);
        return this.diagnosticJpaRepository.save(diagnostic);
    }

    public List<Diagnostic> retrievesAll(Long id){
        System.out.println("infra id" + id);
        return this.diagnosticJpaRepository.findAllDiagnosticsBy(id);
    }

}
