package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.stage.Stage;
import com.kamikarow.hairCareProject.domain.stage.StageInterface;
import com.kamikarow.hairCareProject.infra.RoutineDao;
import com.kamikarow.hairCareProject.infra.StageDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import com.kamikarow.hairCareProject.utility.exception.AccessRightsException;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StageService implements StageInterface {

    private final StageDao stageDao;

    private final RoutineService routineService;
    private final UserService userService;
    private final JwtService jwtService;


    @Override
    public Stage createStage(Stage stage, Long routineId, String token) {
        Routine routine = findRoutineBy(routineId);
        User createdBy = routine.getCreatedBy();

        if(isOwner(getProfil(token), createdBy)){
            stage.setCreatedOn(LocalDateTime.now());
            stage.setRoutine(routine);
            return  save(stage);
        }else{
            throw new AccessRightsException("The client does not have access rights to the content");
        }
    }
    @Override
    public Stage update(Stage stage, String token) {

        Stage stageFromDataBase= findStageByStageId(stage.getId());
            Routine routine = findRoutineBy(stageFromDataBase.getRoutine().getId());
            User createdBy = routine.getCreatedBy();

            if(isOwner(getProfil(token), createdBy)){
                stage.setCreatedOn(stageFromDataBase.getCreatedOn());
                stage.setRoutine(routine);
                return  updateStage(stage);
            }else{
                throw new AccessRightsException("The client does not have access rights to the content");
            }
    }
    @Override
    public void delete(@RequestBody Long stageId, String token) {
        User createdBy = findUserByStageId(stageId);
        if(getProfil(token) == createdBy){
            deleteStageByStageID(stageId);
        }else{
            throw new AccessRightsException("The client does not have access rights to the content");
        }
    }

    /****         Utils methods         **/

    private boolean isOwner(User a, User b){
        return a == b;
    } //todo replace by natif  equals method based on email
    private Stage save(Stage stage) { //todo : changer la visibilité du package
        return  stageDao.save(stage);
    }
    private Stage updateStage(Stage stage) { //todo : changer la visibilité du package
        return  stageDao.update(stage);
    }
    private  Routine findRoutineBy(Long routineId){
        Routine routine = routineService.findBy(routineId);
        if(routine==null)
            throw new RessourceNotFoundException("Cannot find the requested resource"); //
        return routine;
    }
    private Stage findStageByStageId (Long stageId){

        Stage stage = stageDao.findBy(stageId);
        if(stage==null)
            throw new RessourceNotFoundException("Cannot find the requested resource"); //
        return stage;
    }
    private User findUserByStageId(Long id)  {
        return findStageByStageId(id).getRoutine().getCreatedBy();
    }
    private void deleteStageByStageID(Long stageId){
        this.stageDao.delete(stageId);
    }
    private User getProfil(String token){
        return userService.getProfil(getEmail(token)).orElseThrow();
    }
    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
