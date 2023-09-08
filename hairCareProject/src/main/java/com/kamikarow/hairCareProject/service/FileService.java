package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.config.JwtService;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.file.File;
import com.kamikarow.hairCareProject.domain.file.FileInterface;
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
    private final DiagnosticDao diagnosticDao;
    private final UserDao userDao;
    private final FileDao fileDao;

    private final JwtService jwtService;

    @Override
    public FileResponse save(FileRequest fileRequest, String token) {
        System.out.println("debbug service "+ fileRequest);
        //String email = getEmail(token);
        //User user = userDao.findByEmail(email);
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setId(fileRequest.getDiagnostic().getId());
        fileRequest.setDiagnostic(diagnostic);
        return new FileResponse().toFileResponse(fileDao.save(fileRequest.toFile()));
    }


    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
