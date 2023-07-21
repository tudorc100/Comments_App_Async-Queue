package com.example.demo.listener;

import java.nio.charset.StandardCharsets;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class QueueConsumer {
    private final CommentService commentService;
    public void readMessages() throws Exception, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("cow.rmq2.cloudamqp.com");
        factory.setUsername("vxilgfmz");
        factory.setPassword("EwpCsbrfWMLfPuazWlJQ0E8xLPrxdx-j");
        factory.setPort(5672);
        factory.setUri("amqps://vxilgfmz:EwpCsbrfWMLfPuazWlJQ0E8xLPrxdx-j@cow.rmq2.cloudamqp.com/vxilgfmz");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare("CommentsApp", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + message + "'");

            try {
                Thread.sleep(400);
                JSONObject jsonObject = new JSONObject(message);
                commentService.addComment(Comment.builder().text((String) jsonObject.get("text")).build());
                Thread.sleep(400);
            } catch (InterruptedException | JSONException e) {
                throw new RuntimeException(e);
            }
        };
        channel.basicConsume("CommentsApp", true, deliverCallback, consumerTag -> {
        });
    }
}
