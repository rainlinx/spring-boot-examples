package com.rainlin.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    private SimpMessageSendingOperations sendingOperations;

    public GreetingController(SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("/ws/send")
    @ResponseBody
    public void send() {
        sendingOperations.convertAndSend("/topic/greetings",
                new Greeting("Hello, " + HtmlUtils.htmlEscape("it`s my message") + "!"));
    }

    @GetMapping("/ws/sendToUser/{userName}")
    @ResponseBody
    public void send(@PathVariable String userName) {
        sendingOperations.convertAndSendToUser(userName, "/greetings",
                new Greeting("Hello, " + HtmlUtils.htmlEscape("it`s my message") + "!"));
    }
}
