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

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
public class RedisHambombLockImpl implements HambombLock {

    private RedisTemplate<String,Object> redisTemplate;

    public RedisHambombLockImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock() {

        System.out.println("RedisHambombLockImpl lock");

        return true;
    }

    @Override
    public boolean unlock() {

        System.out.println("RedisHambombLockImpl unlock");

        return true;
    }
}
