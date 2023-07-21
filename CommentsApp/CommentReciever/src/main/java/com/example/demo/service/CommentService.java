package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
        private final CommentRepository commentRepository;

        public CommentService(CommentRepository commentRepository) {
            this.commentRepository = commentRepository;
        }

        public Comment addComment(Comment comment) {
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        }

        public List<Comment> getAllComments() {
            return commentRepository.findAll();
        }

}
