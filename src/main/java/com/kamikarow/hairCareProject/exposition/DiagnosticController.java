package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticRequest;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import com.kamikarow.hairCareProject.service.DiagnosticService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/diagnostics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DiagnosticController {

    //private static final Logger logger = LogManager.getLogger(DiagnosticController.class);
    private final DiagnosticService diagnosticService;
    private final BearerTokenWrapper tokenWrapper;

    @GetMapping
    public ResponseEntity<List<DiagnosticResponse>> retrieveAllDiagnostics() throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(new DiagnosticResponse().toListOfDiagnosticResponse(this.diagnosticService.retrieveAllDiagnostics(token)));
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PreAuthorize("hasRole('SUB_CONTRACTOR')")
    @PostMapping //todo role subcontractor
    public ResponseEntity<Optional<DiagnosticResponse>> saveDiagnostic(@Valid @RequestBody DiagnosticRequest diagnosticRequest) throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(Optional.ofNullable(new DiagnosticResponse().toDiagnosticResponse(this.diagnosticService.save(diagnosticRequest.toDiagnostic(), token, diagnosticRequest.getClient()))));
        }catch(Exception e){
            throw new Exception(e);
        }
    }


    private String getToken () throws Exception {
        try{
            return tokenWrapper.getToken();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}