package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.file.File;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.file.FileInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.FileRequest;
import com.kamikarow.hairCareProject.exposition.DTO.FileResponse;
import com.kamikarow.hairCareProject.infra.DiagnosticDao;
import com.kamikarow.hairCareProject.infra.FileDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService implements FileInterface {

    private static final Logger logger = LogManager.getLogger(FileService.class);
    private final UserDao userDao;
    private final FileDao fileDao;
    private final DiagnosticDao diagnosticDao;

    private final JwtService jwtService;

    @Override
    public File save(File fileRequest, String token) {

        String email = getEmail(token);
        User createdBy = new User().toUser(findBy(email));

        var idDiagnostic = fileRequest.getDiagnostic().getId();
        Diagnostic diagnostic = new Diagnostic();
        //todo : check if problem
        diagnostic.setId(idDiagnostic);
        diagnostic.setOwner(findOwnerOf(idDiagnostic));

        fileRequest.setDiagnostic(diagnostic);
        fileRequest.setCreatedBy(createdBy);

        return save(fileRequest);
    }

    /****         Utils methods         **/

    private Optional<User> findBy(String email){
      return userDao.findBy(email);
    }

    private User findOwnerOf(Long idDiagnostic){
       return diagnosticDao.findOwnerOf(idDiagnostic);
    }

    private File save(File file){
        return fileDao.save(file);

    }
    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
