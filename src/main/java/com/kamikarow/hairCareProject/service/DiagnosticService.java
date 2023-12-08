package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.diagnostic.DiagnosticInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticRequest;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import com.kamikarow.hairCareProject.infra.DiagnosticDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosticService implements DiagnosticInterface {
    private final DiagnosticDao diagnosticDao;

    private final UserService userService;
    private final JwtService jwtService;


    @Override
    public Diagnostic save(Diagnostic diagnosticRequest, String token, String usernameClient) {
        User creator = new User().toUser(findBy(getEmail(token)));
        if(creator==null){
            throw new UnauthorizedException("The client must authenticate itself to get the requested response");
        }

        User client = new User().toUser(findBy(usernameClient));
        if(client==null){
            throw new RessourceNotFoundException("Cannot find the requested client");
        }
        diagnosticRequest.setOwner(client);
        diagnosticRequest.setCreatedBy(creator);

        return save(diagnosticRequest);
    }

    @Override
    public List<Diagnostic> retrieveAllDiagnostics(String token) {
        Optional<User> user = findBy(getEmail(token));
        if(user==null){
            throw new UnauthorizedException("The client must authenticate itself to get the requested response");
        }
        return retrievesAll(user.get().getId());
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
        return userService.findBy(email);
    }
}