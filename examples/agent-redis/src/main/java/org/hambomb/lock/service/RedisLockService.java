/*
 * Copyright 2019 The  Project
 *
 * The   Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.hambomb.lock.service;

import org.hambomb.lock.HambombLock;
import org.hambomb.lock.HambombLockFactory;
import org.hambomb.lock.RedisHambombLockImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
@Service
public class RedisLockService {

    @Autowired
    private HambombLockFactory hambombLockFactory;

    private static final Logger LOG = LoggerFactory.getLogger(RedisLockService.class);

    public void modifyPhone() {
        HambombLock lock = hambombLockFactory.create("HUAWEI", null,null);

        if (lock.lock()) {
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                lock.unlock();
            }

            LOG.info("Thread is: {} 获取到锁！", Thread.currentThread().getId());

        } else {
            LOG.info("Thread is: {} 未获取到锁！", Thread.currentThread().getId());
        }

    }
}
