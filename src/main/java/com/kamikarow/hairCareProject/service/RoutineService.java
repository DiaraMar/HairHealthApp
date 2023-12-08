package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.routine.RoutineInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.infra.RoutineDao;
import com.kamikarow.hairCareProject.domain.stage.Stage;
import com.kamikarow.hairCareProject.infra.StageDao;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoutineService implements RoutineInterface {

    private final RoutineDao routineDao;
        private final StageDao stageDao;


    private final UserService userService;

    private final JwtService jwtService;



    @Override
    public Routine createRoutine(Routine routine, String token) {
        routine.setCreatedBy(getUser(token).get());
        routine.setCreatedOn(LocalDateTime.now());
        List<Stage> stages = routine.getStages();
        List<Stage> stagesSaved = new ArrayList<>();
        routine.setVisible(false);
        routine.setFavorite(false);
        routine.setStages(null);
        Routine routineSaved = save(routine);

        if(stages!=null){
            for (Stage stage : stages){
                stage.setRoutine(routineSaved);
                stage.setCreatedOn(LocalDateTime.now());
                stagesSaved.add(save(stage));
            }
            routineSaved.setStages(stagesSaved);
        }

        return routineSaved;
    }

    @Override
    public List<Routine> retrieveAll(String token) {
        return retrieveAllBy(getUser(token).get().getId());
    }


    @Override
    public Routine updateRoutine(Routine routine, String token) {
        Optional<User> authenticatedUser = getUser(token);

        if(findBy(routine.getId()).getCreatedBy()== authenticatedUser.get()){

            for (Stage stage : routine.getStages()){
                stage.setRoutine(routine);  //todo stage useless
            }
            routine.setCreatedBy(authenticatedUser.get());
            routine = update(routine);
        }
            
            return routine;
    }

    @Override
    public void deleteRoutine(Long id, String token) {
        if(findBy(id).getCreatedBy()== getUser(token).get()){
            deleteRoutineByRoutineId(id);
        }
        else{
            throw new UnauthorizedException("Unhautorized");
        }
    }
    @Override
    public void deleteStageOfRoutine(Long routineId) {
        for (Stage stage : findAllStagesByRoutineId(routineId)){
            deleteStage(stage);
        }
    }

    /****         Utils methods         **/

    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }
    private Optional<User> getUser(String token){

        Optional<User> user = this.userService.findBy(getEmail(token));
        if(user.isEmpty())
            throw new UnauthorizedException("The client must authenticate itself to perform request");

        return user;
    }

    private Routine save(Routine routine){
        return this.routineDao.save(routine);
    }

    private Stage save(Stage stage){
        return this.stageDao.save(stage);
    }

    private Routine update(Routine routine){
        return this.routineDao.update(routine);

    }
    private List<Routine> retrieveAllBy(Long userId){
        return this.routineDao.retrieveAllBy(userId);
    }
    private void deleteRoutineByRoutineId(Long routineId){
        deleteStageOfRoutine(routineId);
         this.routineDao.delete(Routine.builder().id(routineId).build());
    }

    private List<Stage> findAllStagesByRoutineId(Long routineID){
        return this.stageDao.findAllStagesByRoutineID(routineID);
    }

    private void deleteStage(Stage stage){
        this.stageDao.delete(stage);
    }

    public Routine findBy(Long routineId){
        Optional<Routine> routine = routineDao.findBy(routineId);
        if(routine.isEmpty())
            throw new RessourceNotFoundException("Cannot find the requested resource");
        return routine.get();
    }

}
