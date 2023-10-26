package com.kamikarow.hairCareProject.domain.diagnostic;
import java.util.List;

public interface DiagnosticInterface {


    public Diagnostic save(Diagnostic diagnosticRequest, String token, String usernameClient);
    public List<Diagnostic> retrieveAllDiagnostics(String token);
}
