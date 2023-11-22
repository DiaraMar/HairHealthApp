package com.kamikarow.hairCareProject.domain.stage;

public interface StageInterface {
    Stage createStage (Stage stage, Long routineId, String token);
    Stage update (Stage stage, String token);
    void delete (Long id, String token);
}
