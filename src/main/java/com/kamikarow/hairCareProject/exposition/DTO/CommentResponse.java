package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.domain.routine.Routine;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String createdBy;
    @NotBlank
    private String content;
    private LocalDateTime createdOn;

    public CommentResponse toCommentResponse (Comment comment){
        return CommentResponse
                .builder()
                .id(comment.getId())
                .createdBy(comment.getCreatedBy().getLastname())
                .content(comment.getContent())
                .createdOn(comment.getCreatedOn())
                .build();

    }

    public Comment toComment (CommentResponse commentResponse){
        return Comment
                .builder()
                .id(commentResponse.getId())
                .content(commentResponse.getContent())
                .createdOn(commentResponse.getCreatedOn())
                .build();

    }
}
