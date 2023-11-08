package com.rainlin;

import com.rainlin.config.MemcachedRunner;
import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RepositoryTests {

    @Resource
    private MemcachedRunner memcachedRunner;

    @Test
    public void testSetGet() {
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        memcachedClient.set("testkey", 1000, "666666");
        System.out.println("***********  " + memcachedClient.get("testkey").toString());
    }

}