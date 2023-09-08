package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.file.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    //TODO :notblank

    private String title;

    private String fileExtension;

    private byte[] document;

    private LocalDateTime createdOn;

    public FileResponse toFileResponse(File file){
        return FileResponse.builder()
                .title(file.getTitle())
                .fileExtension(file.getFileExtension())
                .document(file.getDocument())
                .createdOn(file.getCreatedOn())
                .build();
    }
    public FileResponse toFile(){
        return FileResponse.builder()
                .title(title)
                .fileExtension(fileExtension)
                .document(document)
                .createdOn(createdOn)
                .build();
    }
}
