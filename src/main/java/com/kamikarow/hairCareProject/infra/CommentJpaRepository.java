package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment,Long> {

    @Override
    <S extends Comment> S save(S entity);

    @Override
    void delete(Comment entity);

    @Override
    Optional<Comment> findById(Long aLong);
}
