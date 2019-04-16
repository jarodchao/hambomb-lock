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

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
public class RedisHandler implements AgentHandler {

    private RedisTemplate<String,Object> redisTemplate;

    private Duration timeout;

    public RedisHandler(RedisTemplate<String,Object> redisTemplate, Duration timeout) {
        this.redisTemplate = redisTemplate;
        this.timeout = timeout;
    }

    @Override
    public Locked lock(String key) {


        redisTemplate.opsForValue().setIfAbsent(key, key, timeout);

        return null;
    }

    @Override
    public boolean unlock(String key) {

        return redisTemplate.delete(key);
    }
}
