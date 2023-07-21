package com.example.messagingstompwebsocket;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;
@Service
public class QueueSender {
    private static final String QUEUE_NAME="CommentsApp";



    public QueueSender(
    )
    {

    }
    public void send(String comment) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject deviceMessage = new JSONObject();
        deviceMessage.put("text",comment);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("vxilgfmz");
        connectionFactory.setPassword("EwpCsbrfWMLfPuazWlJQ0E8xLPrxdx-j");
        connectionFactory.setPort(5672);
        connectionFactory.setUri("amqps://vxilgfmz:EwpCsbrfWMLfPuazWlJQ0E8xLPrxdx-j@cow.rmq2.cloudamqp.com/vxilgfmz");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, deviceMessage.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + deviceMessage + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
