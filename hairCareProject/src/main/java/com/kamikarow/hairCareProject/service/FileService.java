package com.kamikarow.hairCareProject.service;

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

@Service
@RequiredArgsConstructor
public class FileService implements FileInterface {

    private static final Logger logger = LogManager.getLogger(FileService.class);
    private final UserDao userDao;
    private final FileDao fileDao;
    private final DiagnosticDao diagnosticDao;

    private final JwtService jwtService;

    @Override
    public FileResponse save(FileRequest fileRequest, String token) {

        String email = getEmail(token);

        User createdBy = new User().toUser(userDao.findByEmail(email));

        var idDiagnostic = fileRequest.getDiagnostic().getId();

        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setId(idDiagnostic);
        System.out.println("IV");
        System.out.println(diagnosticDao.owner(idDiagnostic));
        diagnostic.setOwner(diagnosticDao.owner(idDiagnostic));
        System.out.println("V");

        fileRequest.setDiagnostic(diagnostic);

        fileRequest.setCreatedBy(createdBy);

        System.out.println("debbug service" +fileRequest.getTitle());

        new FileResponse().toFileResponse(fileDao.save(fileRequest.toFile()));
        return null;
    }


    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
