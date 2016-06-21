package ram.edu.springboot.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class AppController {

	@RequestMapping("/question")
	public SseEmitter sendEvent() throws Exception {
		SseEmitter sseEmitter = new SseEmitter();
		sseEmitter.send("Message #2");
		return sseEmitter;
	}

	@RequestMapping("/sse")
	public String sse() {
		return "sse";
	}

	@RequestMapping("/ws")
	public String webSocket() {
		return "wsind";
	}

	@RequestMapping("/stomp")
	public String stomp() {
		return "stomp";
	}

	@MessageMapping("/hello")
	@SendTo("/topic")
	public String stompClient(String message) {
		System.out.println(message);
		return "Hello " + message;
	}

}
