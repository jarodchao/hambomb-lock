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
package org.hambomb.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
public class RedisHambombLockImpl implements HambombLock {

    private static final String LOCK_PATH = "hambomb:locks";

    private String LockKey;

    private RedisTemplate<String,Object> redisTemplate;

    private Duration waitTimeout;

    private Duration lockTimeout;

    private static final Logger LOG = LoggerFactory.getLogger(RedisHambombLockImpl.class);

    public RedisHambombLockImpl(String lockKey, RedisTemplate<String, Object> redisTemplate,
                                Duration waitTimeout, Duration lockTimeout) {
        this.LockKey = getLockPath(lockKey);
        this.redisTemplate = redisTemplate;
        this.waitTimeout = waitTimeout;
        this.lockTimeout = lockTimeout;
    }

    @Override
    public boolean lock() {

        if (LOG.isDebugEnabled()) {
            LOG.debug("RedisHambombLockImpl lock");
        }

        boolean locked = redisTemplate.opsForValue().setIfAbsent(this.LockKey, this.LockKey, lockTimeout);

        if (locked) {
            return true;
        }else {
            if (waitTimeout != null && !waitTimeout.isZero()) {

                try {
                    LOG.debug("wait1");
                    waitTimeout.wait();
                    LOG.debug("wait2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return redisTemplate.opsForValue().setIfAbsent(this.LockKey, this.LockKey, lockTimeout);
            }
        }

        return false;
    }

    @Override
    public boolean unlock() {

        Assert.notNull(this.LockKey,"LockKey must not be null!");

        if (redisTemplate.hasKey(this.LockKey)) {
            System.out.println("还未超时，手动删除key.");
            return redisTemplate.delete(this.LockKey);
        }

        System.out.println("超时，自动删除key.");
        return true;

    }

    private String getLockPath(String lockKey) {
        StringBuilder stringBuilder = new StringBuilder(LOCK_PATH);
        stringBuilder.append(":");
        stringBuilder.append(lockKey);
        return stringBuilder.toString();
    }
}
