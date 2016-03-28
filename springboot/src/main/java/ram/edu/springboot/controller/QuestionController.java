package ram.edu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class QuestionController {

	@RequestMapping("/question")
	public SseEmitter sendEvent() throws Exception {
		SseEmitter sseEmitter = new SseEmitter();
		sseEmitter.send("Message #1");
		return sseEmitter;
	}

	@RequestMapping("/start")
	public String index() {
		return "index";
	}
}
