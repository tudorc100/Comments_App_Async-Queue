package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class CommentController {

	private static final QueueSender queueSender = new QueueSender();
	@MessageMapping("/hello")
	public void comment(Comment comment) throws Exception {
		//System.out.println(HtmlUtils.htmlEscape(comment.getText()));
		queueSender.send(comment.getText());
	}


}
