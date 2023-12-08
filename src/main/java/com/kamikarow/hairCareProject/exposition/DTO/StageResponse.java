package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.stage.Stage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StageResponse {

    @Min(1) /** @Min(1) ensure it's a not null value and it's a positive value*/
    private Long id;
    @NotBlank
    private String title;
    private String description;
    private LocalDateTime createdOn;



    public StageResponse toStageResponse(Stage stage){
        return StageResponse
                .builder()
                .id(stage.getId())
                .title(stage.getTitle())
                .description(stage.getDescription())
                .createdOn(stage.getCreatedOn())
                .build();
    }

    public List<StageResponse> toStageResponses(List<Stage> stages){
        List<StageResponse> stagesResponses = new ArrayList<>();
        for (Stage stage: stages){
            stagesResponses.add(toStageResponse(stage));
        }
        return stagesResponses;
    }

    public Stage toStage(){
        return toStage(this);
    }

    private Stage toStage(StageResponse stageResponse){
        return Stage.builder()
                .id(stageResponse.id)
                .title(stageResponse.title)
                .description(stageResponse.description)
                .createdOn(stageResponse.createdOn)
                .build();
    }
    public List<Stage> toStage(List<StageResponse> stageResponses){
        List<Stage> stages = new ArrayList<>();
        for (StageResponse stageResponse: stageResponses){
            stages.add(toStage(stageResponse));
        }
        return stages;
    }

}
