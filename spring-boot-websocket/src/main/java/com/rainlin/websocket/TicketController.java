package com.rainlin.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint("/tickets")
public class TicketController {

    private AtomicInteger tickets = new AtomicInteger(100);

    @OnMessage
    public void getRemainingTickets(Session session, String msg) throws IOException {
        log.info(this.toString());
        session.getBasicRemote().sendText(String.valueOf(tickets.decrementAndGet()));
    }
}
