package com.example.demo;

import com.example.demo.listener.QueueConsumer;
import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final CommentRepository commentRepository;

    private final CommentService commentService;
    @Value("false")
    private Boolean bootstrap;

    @Value("true")
    private Boolean read;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap)
        {
            commentRepository.deleteAll();
            Comment comment1 = Comment.builder().text("First Comment").build();
            Comment comment2 = Comment.builder().text("Second Comment").build();
            commentRepository.saveAll(List.of(comment1, comment2));
        }


        if (read)
        {
            QueueConsumer q=new QueueConsumer(commentService);
            try {
                q.readMessages();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
