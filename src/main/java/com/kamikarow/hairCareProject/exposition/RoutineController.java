package com.kamikarow.hairCareProject.exposition;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.exposition.DTO.RoutineRequest;
import com.kamikarow.hairCareProject.exposition.DTO.RoutineResponse;
import com.kamikarow.hairCareProject.service.RoutineService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/routines")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RoutineController {

    //private static final Logger logger = LogManager.getLogger(RoutineController.class);
    private final RoutineService routineService;

    private final BearerTokenWrapper tokenWrapper;
    @PostMapping
    public ResponseEntity<Optional<RoutineResponse>> saveRoutine(@RequestBody RoutineRequest routineRequest) throws Exception {
        try{
            return ResponseEntity.ok(Optional.ofNullable(new RoutineResponse().toRoutineResponse(createRoutine(routineRequest.toRoutine()))));
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping
    public ResponseEntity<Optional<RoutineResponse>> update(@RequestBody RoutineResponse routineResponse) throws Exception {
        try{
            return ResponseEntity.ok(Optional.ofNullable(new RoutineResponse().toRoutineResponse(updateRoutine(new RoutineResponse().toRoutine(routineResponse)))));
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<RoutineResponse>> retrieveAllRoutine() throws Exception {
        try{
            return ResponseEntity.ok(new RoutineResponse().toRoutineResponses(retrieveAll()));
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteRoutine(@RequestBody Map<String, Long> requestBody) throws Exception {

        try{
            Long id = requestBody.get("id");
            deleteRoutineById(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (UnauthorizedException unauthorizedException) {
            throw  unauthorizedException;
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/delete/all")
    public ResponseEntity deleteMultipleRoutine(@RequestBody List<Long> routineIds) throws Exception {
        try{
            for (Long id : routineIds) {
                deleteRoutineById(id);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (UnauthorizedException unauthorizedException) {
            throw  unauthorizedException;}
        catch(Exception e){
            throw new Exception(e);
        }
    }

    /** Utils Methods **/

    private Routine createRoutine(Routine routine) throws Exception {
        return this.routineService.createRoutine(routine, getToken());
    }

    public List<Routine> retrieveAll() throws Exception {
        return this.routineService.retrieveAll(getToken());
    }


    private Routine updateRoutine(Routine routine) throws Exception {
        return this.routineService.updateRoutine(routine, getToken());
    }
    private void deleteRoutineById(Long id) throws Exception{
        this.routineService.deleteRoutine(id, getToken());
    }



    private String getToken () {
        String token =  tokenWrapper.getToken();

        if(token.isEmpty())
            throw new UnauthorizedException("");
        return token;
    }

}
