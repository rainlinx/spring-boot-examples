package com.rainlin.rabbitmq;

import com.rainlin.rabbit.many.NeoSender;
import com.rainlin.rabbit.many.NeoSender2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ManyTest {
    @Autowired
    private NeoSender neoSender;

    @Autowired
    private NeoSender2 neoSender2;

    @Test
    public void oneToMany() throws Exception {
        for (int i = 0; i < 100; i++) {
            neoSender.send(i);
        }
    }

    @Test
    public void manyToMany() throws Exception {
        for (int i = 0; i < 100; i++) {
            neoSender.send(i);
            neoSender2.send(i);
        }
    }

}