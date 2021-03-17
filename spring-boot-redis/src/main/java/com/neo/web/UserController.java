package com.neo.web;

import com.neo.model.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
public class UserController {

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public User getUser() {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123", LocalDateTime.now(), new Date(), Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("从数据库中获取信息");
        return user;
    }

    @RequestMapping("/addUser")
    @CachePut(value="user-key")
    public User addUser() {
        User user=new User("aa@126.com", "aa" + Math.random(), "aa123456", "aa","123", LocalDateTime.now(), new Date(), Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("新增用户");
        return user;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}