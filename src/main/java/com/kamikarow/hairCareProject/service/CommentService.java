package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.domain.comment.CommentInterface;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.infra.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentInterface {

    private final CommentDao commentDao;

    private final JwtService jwtService;
    private final RoutineService routineService;
    private final UserService userService;

    @Override
    public Comment addComment(Comment comment, Long routineId, String token) {

        Routine routine = findRoutine(routineId);
        Optional<User> user = getUser(token);
        if(routine!= null && routine.isVisible() && isValidContent(comment.getContent()) && user.get().getId() != routine.getCreatedBy().getId()){

            comment.setCreatedBy(user.orElseThrow());
            comment.setRoutine(routine);
            comment.setCreatedOn(LocalDateTime.now());
            comment = save(comment);

        }

        return comment;
    }

    @Override
    public void deleteComment(Long commentId, String token) {
        Comment comment = findCommentByCommentId(commentId);
        Optional<User> user = getUser(token);
        if(user.get().getId() == comment.getCreatedBy().getId()){
            delete(comment);
        }

    }



    /****/
    private boolean isValidContent(String content) {
        return content != null && !content.isEmpty();
    }
    private Comment save(Comment comment){
        return this.commentDao.save(comment);
    }
    private void delete(Comment comment){this.commentDao.delete(comment); }

    private Comment findCommentByCommentId (Long commentId){
        return this.commentDao.findBy(commentId).get();
    }

    private Routine findRoutine (Long routineId){
        return this.routineService.findBy(routineId);
    }
    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }
    private Optional<User> getUser(String token){
        return this.userService.findBy(getEmail(token));
    }
}

