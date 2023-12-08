package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.exposition.DTO.StageRequest;
import com.kamikarow.hairCareProject.exposition.DTO.StageResponse;
import com.kamikarow.hairCareProject.service.StageService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;

import com.kamikarow.hairCareProject.utility.exception.AccessRightsException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StageController {


    private final StageService service;
    private final BearerTokenWrapper tokenWrapper;
    private static final Logger logger = LogManager.getLogger(StageController.class);

    @PostMapping("/delete")
    public ResponseEntity deleteStage(@RequestBody Map<String, Long> requestBody) throws Exception {
        try{
            Long id = requestBody.get("id");
            deleteStageBy(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch(AccessRightsException accessRightsException){
            throw accessRightsException;
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/delete/all")
    public ResponseEntity deleteMultipleStages(@RequestBody List<Long> stages) throws Exception {
        try{
            for (Long id : stages) {
                deleteStageBy(id);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch(AccessRightsException accessRightsException){
            throw accessRightsException;
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping
    public ResponseEntity<StageResponse> addStage(@RequestBody StageRequest stageRequest) throws Exception {
        try{
            return new ResponseEntity<>(new StageResponse().toStageResponse(this.service.createStage(stageRequest.toStage(), stageRequest.getRoutineId(), getToken())),HttpStatus.OK);
        }catch(AccessRightsException accessRightsException){
            throw accessRightsException;
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping
    public ResponseEntity<StageResponse> update(@Valid @RequestBody StageResponse stageResponse)throws Exception {
        try{
            return new ResponseEntity<>(new StageResponse().toStageResponse(this.service.update(stageResponse.toStage(), getToken())),HttpStatus.OK);
        }catch(AccessRightsException accessRightsException){
            throw accessRightsException;
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    /** Utils Methods **/

    private void deleteStageBy(Long id) throws Exception{
        this.service.delete(id, getToken());
    }
    private String getToken () {
        String token =  tokenWrapper.getToken();

        if(token.isEmpty())
            throw new UnauthorizedException("");
        return token;
    }

}
