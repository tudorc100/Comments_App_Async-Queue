package com.example.demo.ServiceUnitTests;

import com.example.demo.service.CommentService;
import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @Test
    public void testGetAllComments() {
        // Mock the CommentRepository
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        CommentService commentService = new CommentService(commentRepository);

        // Define some test comments
        Comment comment1 = Comment.builder().text("First Comment").build();
        Comment comment2 = Comment.builder().text("Second Comment").build();
        List<Comment> comments = Arrays.asList(comment1, comment2);

        // Stub the findAll method to return the test comments
        when(commentRepository.findAll()).thenReturn(comments);

        // Call the getAllComments method and verify the result
        List<Comment> resultComments = commentService.getAllComments();
        assertEquals(2, resultComments.size());
        assertEquals("First Comment", resultComments.get(0).getText());
        assertEquals("Second Comment", resultComments.get(1).getText());
    }

    @Test
    public void testAddComment() {
        // Mock the CommentRepository
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        CommentService commentService = new CommentService(commentRepository);

        // Define a test comment
        Comment comment = Comment.builder().text("Test Comment").build();;

        // Stub the save method to return the test comment with an ID
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment(1L, "Test comment"));

        // Call the addComment method and verify the result
        Comment resultComment = commentService.addComment(comment);
        assertEquals(1L, resultComment.getId());
        assertEquals("Test comment", resultComment.getText());
    }
}