package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.config.JwtService;
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

        User client = new User().toUser(userDao.findByEmail(diagnosticRequest.getClient()));
/*
        User creator = new User().toUser(userDao.findByEmail(getEmail(token)));
*/
        return this.diagnosticDao.save(new DiagnosticRequest().toDiagnostic(diagnosticRequest, client, null ));
    }

    @Override
    public List<DiagnosticResponse> retrieveAllDiagnostics(String token) {
       Optional<User> user = userDao.findByEmail(getEmail(token));
        return new DiagnosticResponse().toListOfDiagnosticResponse(this.diagnosticDao.retrievesAll(user.get().getId()));
    }

    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
