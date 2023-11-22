package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.stage.Stage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StageRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private Long routineId;


    public Stage toStage(){
        return toStage(this);
    }
    private Stage toStage(StageRequest stageRequest){
        return Stage
                .builder()
                .title(stageRequest.getTitle())
                .description(stageRequest.description)
                .build();
    }

    public List<Stage> toStagesRequest(List<StageRequest> stageRequests){
        List<Stage> stages =new ArrayList<>();
        for (StageRequest stageRequest : stageRequests){
            stages.add(toStage(stageRequest));
        }

        return stages;
    }
}
