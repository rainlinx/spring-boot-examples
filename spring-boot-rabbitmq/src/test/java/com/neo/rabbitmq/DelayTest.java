package com.neo.rabbitmq;

import com.neo.rabbit.delay.DelaySender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DelayTest {

    @Autowired
    private DelaySender sender;

    @Test
    public void delaySender() throws Exception {
        sender.send();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        delay(countDownLatch);
        //delay();
        countDownLatch.await();
    }

    /**
     * 使用Future的阻塞特性
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void delay() throws InterruptedException, ExecutionException {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("wake up");
        }).get();
    }

    /**
     * 使用CountDownLatch的阻塞特性
     * @param countDownLatch
     */
    private static void delay(CountDownLatch countDownLatch) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("wake up");
            countDownLatch.countDown();
        });
    }
}