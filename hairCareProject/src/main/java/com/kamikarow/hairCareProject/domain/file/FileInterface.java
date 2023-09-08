package com.kamikarow.hairCareProject.domain.file;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticRequest;
import com.kamikarow.hairCareProject.exposition.DTO.DiagnosticResponse;
import com.kamikarow.hairCareProject.exposition.DTO.FileRequest;
import com.kamikarow.hairCareProject.exposition.DTO.FileResponse;

import java.util.List;

public interface FileInterface {

    public FileResponse save(FileRequest fileRequest, String token);
    /*
    public List<File> retrieveAllfilesByOwner(String token);
    public List<File> retrieveAllFilesByCreatedBy(String token);
    public File retrieveByCreatorId(String token);
    */
}
