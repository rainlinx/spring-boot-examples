package com.rainlin.rabbitmq;

import com.rainlin.model.User;
import com.rainlin.rabbit.object.ObjectSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObjectTest {

    @Autowired
    private ObjectSender sender;

    @Test
    public void sendOject() throws Exception {
        User user = new User();
        user.setName("neo");
        user.setPass("123456");
        sender.send(user);
    }

}