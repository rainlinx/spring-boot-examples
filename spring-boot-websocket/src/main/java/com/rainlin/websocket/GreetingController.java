package com.rainlin.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    private final SimpMessageSendingOperations sendingOperations;

    public GreetingController(SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        // simulated delay
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("/ws/send")
    @ResponseBody
    public void send(@RequestParam(defaultValue = "/topic/greetings") String destination) {
        sendingOperations.convertAndSend(destination,
                new Greeting("Hello, " + HtmlUtils.htmlEscape("it`s my message") + "!"));
    }

    /**
     * 内存消息代理使用
     */
    @GetMapping("/ws/send/user/{userName}")
    @ResponseBody
    public void sendToUser(@PathVariable String userName, @RequestParam(defaultValue = "/greetings") String destination) {
        sendingOperations.convertAndSendToUser(userName, destination,
                new Greeting("Hello, " + HtmlUtils.htmlEscape("it`s my message") + "!"));
    }
}
