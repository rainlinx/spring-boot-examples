package com.rainlin.web;

import com.rainlin.util.NeoProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ProPertiesTest {

    @Autowired
    private NeoProperties neoProperties;

    @Test
    public void getHello() throws Exception {
        System.out.println(neoProperties.getTitle());
        Assertions.assertEquals(neoProperties.getTitle(), "纯洁的微笑");
        Assertions.assertEquals(neoProperties.getDescription(), "分享生活和技术");
    }

    @Test
    public void testMap() throws Exception {
        Map<String, Long> orderMinTime = new HashMap<String, Long>();
        long xx = orderMinTime.get("123");
    }

}