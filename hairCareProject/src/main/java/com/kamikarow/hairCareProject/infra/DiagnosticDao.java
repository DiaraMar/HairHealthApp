package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DiagnosticDao {

    private final DiagnosticJpaRepository diagnosticJpaRepository;

    public Diagnostic save(Diagnostic diagnostic){
        return this.diagnosticJpaRepository.save(diagnostic);
    }

    public User findOwnerOf(Long idDiagnostic){
        System.out.println("dao");
        //todo recuperation user
       return this.diagnosticJpaRepository.findById(idDiagnostic).get().getOwner();
    }

    public List<Diagnostic> retrievesAll(Long id){
        return this.diagnosticJpaRepository.findAllDiagnosticsBy(id);
    }

}
