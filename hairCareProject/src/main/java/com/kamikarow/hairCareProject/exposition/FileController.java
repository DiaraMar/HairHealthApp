package com.kamikarow.hairCareProject.exposition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamikarow.hairCareProject.domain.file.File;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import com.kamikarow.hairCareProject.exposition.DTO.FileRequest;
import com.kamikarow.hairCareProject.exposition.DTO.FileResponse;
import com.kamikarow.hairCareProject.service.FileService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private static final Logger logger = LogManager.getLogger(FileController.class);
    private final FileService fileService;
    private final BearerTokenWrapper tokenWrapper;


    @PostMapping //TODO //MakeByadmin
    public ResponseEntity<FileResponse> save(
            @RequestParam ("diagnostic")String diagnostic,
            @RequestParam ("title")String title,
            @RequestParam ("fileExtension")String fileExtension,
            @RequestParam ("document") MultipartFile document
    ) throws Exception {
        try{
            String token = getToken ();
            System.out.println("test file endpoint");
            FileRequest fileRequest = new FileRequest().toFileRequest(diagnostic, title,fileExtension, document);
            return ResponseEntity.ok(new FileResponse().toFileResponse(this.fileService.save(fileRequest.toFile(), token)));

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
