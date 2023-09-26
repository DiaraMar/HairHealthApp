package com.kamikarow.hairCareProject.exposition.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.file.File;
import com.kamikarow.hairCareProject.domain.user.User;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {

    private Diagnostic diagnostic;

    private String title;

    private String fileExtension;
    @Lob
    private byte[] document;

    private LocalDateTime createdOn;
    private User createdBy;


    public File toFile(){
        File file =  File.builder()
                .diagnostic(diagnostic)
                .title(title)
                .fileExtension(fileExtension)
                .document(document)
                .createdOn(LocalDateTime.now())
                .owner(diagnostic.getOwner())
                .build();
        System.out.println("debugg filerequest to file  "+ file);
        return file;
    }



    public FileRequest toFileRequest(String diagnosticJSON, String titleInput, String fileExtensionInput, MultipartFile documentInput) throws Exception {


        byte[] newDocument = compressFile(documentInput);

        if (newDocument == null || newDocument.length == 0){
            throw new Exception("Exception");
        }
        Long idDiagnostic = retrieveValue(diagnosticJSON);
        Diagnostic newDiagnostic = new Diagnostic();

        if(idDiagnostic<=0){
            throw new Exception("Exception");
        }

        newDiagnostic.setId(idDiagnostic);

        return FileRequest.builder()
                .diagnostic(newDiagnostic)
                .title(titleInput)
                .fileExtension(fileExtensionInput)
                .document(newDocument)
                .build();
    }

    private Long retrieveValue(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(json);

    // Extraction de la valeur "id"
    return jsonNode.get("id").asLong();
    }

    public byte[] compressFile(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();}



}
