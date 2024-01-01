package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.exposition.DTO.CommentRequest;
import com.kamikarow.hairCareProject.exposition.DTO.CommentResponse;
import com.kamikarow.hairCareProject.service.CommentService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    //private static final Logger logger = LogManager.getLogger(RoutineController.class);
    private final CommentService commentService;

    private final BearerTokenWrapper tokenWrapper;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) throws Exception {
        try{
            return new ResponseEntity<>(new CommentResponse().toCommentResponse(create(commentRequest.toComment(), commentRequest.getRoutineId())), HttpStatus.OK);
        }
        catch (BadRequestException | UnauthorizedException | RessourceNotFoundException | AccessRightsException |
               NotAllowedException exception){
            throw exception;
        }catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping
    public ResponseEntity deleteComment(@RequestBody Map<String, Long> requestBody) throws Exception {
        try{
            Long id = requestBody.get("id");
            this.deleteCommentById(id);
            return new ResponseEntity(HttpStatus.OK);        }
        catch (UnauthorizedException | NotAllowedException exception){
            throw exception;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    /** Utils Methods **/

    private Comment create(Comment comment, Long routineId) throws Exception { //todo commentRequest

        return this.commentService.addComment(comment, routineId, getToken());
    }

    void deleteCommentById(Long commentId) throws Exception{
        this.commentService.deleteComment(commentId, getToken());
    }
    private String getToken () {
        String token =  tokenWrapper.getToken();

        if(token.isEmpty())
            throw new UnauthorizedException("");
        return token;
    }

}
