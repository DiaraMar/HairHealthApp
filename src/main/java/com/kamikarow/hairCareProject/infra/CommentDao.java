package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentDao {

    private final CommentJpaRepository commentJpaRepository;

    public Comment save(Comment comment){
        return this.commentJpaRepository.save(comment);
    }

    public Optional<Comment> findBy(Long commentId){return  this.commentJpaRepository.findById(commentId);}

    public void delete(Comment comment){
        this.commentJpaRepository.delete(comment);
    }
}
