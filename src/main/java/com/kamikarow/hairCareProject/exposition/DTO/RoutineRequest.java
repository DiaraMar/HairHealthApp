package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.routine.Routine;
import jakarta.validation.constraints.NotBlank;
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
public class RoutineRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private List<StageRequest> stages = new ArrayList<>();

    public Routine toRoutine(){
        return Routine.builder()
                .title(this.title)
                .description(description)
                .stages(new StageRequest().toStagesRequest(stages))
                .build();
    }

}
