package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.stage.Stage;
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
public class RoutineResponse {

    private Long id;
    private String title;
    private String description;
    private String createdBy;
    private LocalDateTime createdOn;
    private List<StageResponse> stages;
    private List<Comment> comments;
    private boolean isVisible;
    private boolean isFavorite;


    public RoutineResponse toRoutineResponse(Routine routine){
        RoutineResponse y = RoutineResponse.builder()
                .id(routine.getId())
                .title(routine.getTitle())
                .description(routine.getDescription())
                .createdBy(routine.getCreatedBy().getLastname())
                .createdOn(routine.getCreatedOn())
                .stages(new StageResponse().toStageResponses(routine.getStages()))
                .comments(routine.getComments())
                .isVisible(routine.isVisible())
                .isFavorite(routine.isFavorite())
                .build();
        return y;
    }

    public List<RoutineResponse> toRoutineResponses(List<Routine> routines){
        List<RoutineResponse> routineResponses = new ArrayList<>();
        for(Routine routine : routines){
            routineResponses.add(toRoutineResponse(routine));
        }
        return routineResponses;
    }

    public Routine toRoutine(RoutineResponse routineResponse){
        System.out.println(routineResponse + "debuggggggg 1");
        Routine r = Routine.builder()
                .id(routineResponse.getId())
                .title(routineResponse.getTitle())
                .description(routineResponse.getDescription())
                .createdOn(routineResponse.getCreatedOn())
                .stages(new StageResponse().toStage(routineResponse.getStages()))
                .comments(routineResponse.getComments())
                .isVisible(routineResponse.isVisible())
                .isFavorite(routineResponse.isFavorite())
                .build();

        System.out.println(r + "debuggggggg  2");

        return r;
    }

}
