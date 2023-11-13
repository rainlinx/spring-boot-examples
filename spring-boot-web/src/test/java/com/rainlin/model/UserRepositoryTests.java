package com.rainlin.model;

import com.rainlin.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.util.Date;


@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() throws Exception {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);

        userRepository.save(new User("aa1", "aa@126.com", "aa", "aa123456", formattedDate));
        userRepository.save(new User("bb2", "bb@126.com", "bb", "bb123456", formattedDate));
        userRepository.save(new User("cc3", "cc@126.com", "cc", "cc123456", formattedDate));

        //		Assertions.assertEquals(9, userRepository.findAll().size());
        Assertions.assertEquals("bb2", userRepository.findByUserNameOrEmail("bb", "xxx126.com").getNickName());
        userRepository.delete(userRepository.findByUserName("aa"));
    }

}