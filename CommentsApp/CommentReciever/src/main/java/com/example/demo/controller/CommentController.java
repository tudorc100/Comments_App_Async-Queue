package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
//'origins="*"' not recommended for production
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }
}
