package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.domain.comment.CommentInterface;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.infra.CommentDao;
import com.kamikarow.hairCareProject.utility.exception.*;
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

        if(!isValidContent(comment.getContent()))
            throw new BadRequestException("Cannot not process the request due to something that is perceived to be a client error");//todo overide by this one

        Optional<User> user = getUser(token);
        if(user.isEmpty())
            throw new UnauthorizedException("The client must authenticate itself to get the requested response");//todo overide by this one

        Routine routine = findRoutine(routineId);
        if(routine==null)
            throw new RessourceNotFoundException("Cannot find the requested resource"); //todo overide by this one

        if(!routine.isVisible())
            throw new AccessRightsException("The client does not have access rights to the content");

        if(user.get().getId() == routine.getCreatedBy().getId())
            throw new NotAllowedException("Method not allowed");

            comment.setCreatedBy(user.orElseThrow());
            comment.setRoutine(routine);
            comment.setCreatedOn(LocalDateTime.now());
            comment = save(comment);

        return comment;
    }

    @Override
    public void deleteComment(Long commentId, String token) {
        Comment comment = findCommentByCommentId(commentId); //todo throw error if comment is not found
        Optional<User> user = getUser(token);
        if(user.isEmpty())
            throw new UnauthorizedException("The client must authenticate itself to perform request");//todo overide by this one
        if(user.get().getId() != comment.getCreatedBy().getId())
            throw new NotAllowedException("Method not allowed");
            delete(comment);
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

