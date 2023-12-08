package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticRequest;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import com.kamikarow.hairCareProject.service.DiagnosticService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
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
        try {
            String token = getToken();
            return ResponseEntity.ok(new DiagnosticResponse().toListOfDiagnosticResponse(retrieveAllDiagnostics(token)));
        } catch (UnauthorizedException unauthorizedException) {
            throw  unauthorizedException;
        }
        catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasRole('SUB_CONTRACTOR')")
    @PostMapping //todo role subcontractor
    public ResponseEntity<Optional<DiagnosticResponse>> saveDiagnostic(@Valid @RequestBody DiagnosticRequest diagnosticRequest) throws Exception {
        try {
            String token = getToken();
            return ResponseEntity.ok(Optional.ofNullable(new DiagnosticResponse().toDiagnosticResponse(save(diagnosticRequest.toDiagnostic(), token, diagnosticRequest.getClient()))));
        } catch (UnauthorizedException | RessourceNotFoundException exception) {
            throw  exception;
        } catch (Exception e) {
            throw e;
        }
    }

    /** Utils Methods **/

    private List<Diagnostic> retrieveAllDiagnostics(String token) {
        return this.diagnosticService.retrieveAllDiagnostics(token);
    }

    private Diagnostic save(Diagnostic diagnosticRequest, String token, String usernameClient) {
        return (this.diagnosticService.save(diagnosticRequest, token, usernameClient));
    }



        private String getToken () {
        String token =  tokenWrapper.getToken();

        if(token.isEmpty())
            throw new UnauthorizedException("");
        return token;
    }
}
