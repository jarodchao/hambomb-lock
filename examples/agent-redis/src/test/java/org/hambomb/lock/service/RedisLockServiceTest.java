package org.hambomb.lock.service;

import org.hambomb.lock.AgentRedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgentRedisApplication.class})
public class RedisLockServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLockServiceTest.class);

    @Autowired
    private RedisLockService redisLockService;

    @Test
    public void modifyPhone() {

        redisLockService.modifyPhone();
    }

    @Test
    public void modifyPhoneBy() {

        int count = 1;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                redisLockService.modifyPhone();
                countDownLatch.countDown();
                LOG.info("Thread is: " + Thread.currentThread().getId() + " 准备完毕！");
            });

            thread.start();
        }


    }

}