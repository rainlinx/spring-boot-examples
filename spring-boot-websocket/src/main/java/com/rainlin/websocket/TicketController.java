package com.rainlin.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多例模式
 *
 * @author rainlin
 */
@Slf4j
@Component
@ServerEndpoint("/tickets")
public class TicketController {

    private AtomicInteger tickets = new AtomicInteger(100);

    @OnMessage
    public void getRemainingTickets(Session session, String msg) throws IOException {
        session.getBasicRemote().sendText(String.valueOf(tickets.decrementAndGet()));
    }
}
