package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.exposition.DTO.CommentRequest;
import com.kamikarow.hairCareProject.exposition.DTO.CommentResponse;
import com.kamikarow.hairCareProject.service.CommentService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    private static final Logger logger = LogManager.getLogger(RoutineController.class);
    private final CommentService commentService;

    private final BearerTokenWrapper tokenWrapper;

    @PostMapping
    private ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) throws Exception {
        try{
            return new ResponseEntity<>(new CommentResponse().toCommentResponse(create(commentRequest.toComment(), commentRequest.getRoutineId())), HttpStatus.OK);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping
    private ResponseEntity deleteComment(@RequestBody Map<String, Long> requestBody) throws Exception {
        try{
            Long id = requestBody.get("id");
            this.deleteCommentById(id);
            return new ResponseEntity(HttpStatus.OK);        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    private Comment create(Comment comment, Long routineId) throws Exception {
        return this.commentService.addComment(comment, routineId, getToken());
    }

    private void deleteCommentById(Long commentId) throws Exception{
        this.commentService.deleteComment(commentId, getToken());
    }
    private String getToken () throws Exception {
        try{
            return tokenWrapper.getToken();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

}
