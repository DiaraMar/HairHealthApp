package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.diagnostic.DiagnosticInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticRequest;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import com.kamikarow.hairCareProject.infra.DiagnosticDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosticService implements DiagnosticInterface {
    private static final Logger logger = LogManager.getLogger(DiagnosticService.class);
    private final DiagnosticDao diagnosticDao;
    private final UserDao userDao;
    private final JwtService jwtService;


    @Override
    public Diagnostic save(DiagnosticRequest diagnosticRequest, String token) {
        User client = new User().toUser(findBy(diagnosticRequest.getClient()));
        User creator = new User().toUser(findBy(getEmail(token)));
        return save(new DiagnosticRequest().toDiagnostic(diagnosticRequest, client, creator ));
    }

    @Override
    public List<DiagnosticResponse> retrieveAllDiagnostics(String token) {
        Optional<User> user = findBy(getEmail(token));
        return new DiagnosticResponse().toListOfDiagnosticResponse(retrievesAll(user.get().getId()));
    }

    /****         Utils methods         **/
    private List<Diagnostic> retrievesAll(Long id){
        return this.diagnosticDao.retrievesAll(id);
    }
    private Diagnostic save(Diagnostic diagnostic){
        return  this.diagnosticDao.save(diagnostic);
    }
    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }
    private Optional<User> findBy(String email){
        return userDao.findBy(email);
    }
}