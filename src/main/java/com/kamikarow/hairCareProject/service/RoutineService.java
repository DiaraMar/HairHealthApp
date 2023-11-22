package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.routine.RoutineInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.infra.RoutineDao;
import com.kamikarow.hairCareProject.domain.stage.Stage;
import com.kamikarow.hairCareProject.infra.StageDao;
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

        for (Stage stage : stages){
            stage.setRoutine(routineSaved);
            stage.setCreatedOn(LocalDateTime.now());
            stagesSaved.add(save(stage));
        }

        routineSaved.setStages(stagesSaved);
        return routineSaved;
    }

    @Override
    public List<Routine> retrieveAll(String token) {
        return retrieveAllBy(getUser(token).get().getId());
    }


    @Override
    public Routine updateRoutine(Routine routine, String token) {
        User authenticatedUser = getUser(token).get();
        if(findBy(routine.getId()).getCreatedBy()== authenticatedUser){
            for (Stage stage : routine.getStages()){
                stage.setRoutine(routine);
            }
            routine.setCreatedBy(authenticatedUser);
            update(routine);
        }
            
            return null;
    } //todo implement

    @Override
    public void deleteRoutine(Long id, String token) {
        if(findBy(id).getCreatedBy()== getUser(token).get()){
            deleteRoutineByRoutineId(id);
        }//todo : throw error if else
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
        return this.userService.findBy(getEmail(token));
    }

    private Routine save(Routine routine){
        return this.routineDao.save(routine);
    }

    private Stage save(Stage stage){
        return this.stageDao.save(stage);
    }

    private void update(Routine routine){
        this.routineDao.update(routine);

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
        return routineDao.findBy(routineId).orElseThrow();
    }

}
