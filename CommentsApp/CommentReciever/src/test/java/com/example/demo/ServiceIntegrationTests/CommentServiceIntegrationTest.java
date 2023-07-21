package com.example.demo.ServiceIntegrationTests;


import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommentServiceIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Test
    public void testGetAllComments() {
        // Save some test comments to the database
        Comment comment1 = Comment.builder().text("First Comment").build();
        Comment comment2 = Comment.builder().text("Second Comment").build();
        commentRepository.saveAll(List.of(comment1, comment2));

        // Call the getAllComments method and verify the result
        List<Comment> resultComments = commentService.getAllComments();
        assertEquals(2, resultComments.size());
        assertEquals("First Comment", resultComments.get(0).getText());
        assertEquals("Second Comment", resultComments.get(1).getText());
    }

    @Test
    public void testAddComment() {
        // Define a test comment
        Comment comment = Comment.builder().text("Test Comment").build();;

        // Call the addComment method and verify the result
        Comment resultComment = commentService.addComment(comment);
        assertEquals("Test Comment", resultComment.getText());
    }
}
