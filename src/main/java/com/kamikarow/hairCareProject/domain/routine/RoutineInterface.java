package com.kamikarow.hairCareProject.domain.routine;

import com.kamikarow.hairCareProject.domain.stage.Stage;

import java.util.List;

public interface RoutineInterface {

    Routine createRoutine(Routine routine, String token);

    List<Routine> retrieveAll(String token);

    Routine updateRoutine(Routine routine, String token);

    void deleteRoutine(Long id, String token);
    void deleteStageOfRoutine(Long id);

}
