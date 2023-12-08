package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.comment.Comment;
import com.kamikarow.hairCareProject.exposition.DTO.CommentRequest;
import com.kamikarow.hairCareProject.exposition.DTO.CommentResponse;
import com.kamikarow.hairCareProject.service.CommentService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommentControllerTest {
    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private CommentRequest commentRequest;

    @Test
    public void testAddComment() throws Exception {
        CommentResponse mockCommentResponse = new CommentResponse();
        mockCommentResponse.setId(1L);
        mockCommentResponse.setCreatedBy("User123");
        mockCommentResponse.setContent("This is a comment");
        mockCommentResponse.setCreatedOn(LocalDateTime.now());

        // Mocking commentService.addComment to return the mockCommentResponse
        when(commentService.addComment(any(Comment.class), anyLong(), anyString())).thenReturn(mockCommentResponse.toComment(mockCommentResponse));

        // Mocking the behavior of commentRequest
        // Assuming some specific behavior for commentRequest

        // Testing the addComment method in CommentController
        ResponseEntity<CommentResponse> response = commentController.addComment(commentRequest);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());

        CommentResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(mockCommentResponse.getId(), responseBody.getId());
        assertEquals(mockCommentResponse.getCreatedBy(), responseBody.getCreatedBy());
        assertEquals(mockCommentResponse.getContent(), responseBody.getContent());

        // Verifying that commentService.addComment was called exactly once
        verify(commentService, times(1)).addComment(any(Comment.class), anyLong(), anyString());
    }
}