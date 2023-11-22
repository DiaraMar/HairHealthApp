package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.stage.Stage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StageDao {

    private final StageJpaRepository stageJpaRepository;

    public Stage save(Stage stage){
        return this.stageJpaRepository.save(stage);
    }
    public void delete(Stage stage){
        this.stageJpaRepository.delete(stage);
    }
    public void delete(Long id){
        this.stageJpaRepository.delete(findBy(id));
    }

    public Stage findBy(Long id){
       return this.stageJpaRepository.findById(id).get();
    }

    public List<Stage> findAllStagesByRoutineID(Long routineID){
        return this.stageJpaRepository.findAllByRoutineId(routineID);
    }
    public Stage update(Stage stage){
        return save(stage);
    }
}
