package com.kamikarow.hairCareProject.domain.comment;

public interface CommentInterface {

    public Comment addComment(Comment comment, Long routineId, String token);
    public void deleteComment(Long commentId, String token);


}
