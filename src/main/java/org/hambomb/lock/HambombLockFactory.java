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

import java.time.Duration;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
public interface HambombLockFactory {


    /**
     * 创建HambombLock
     * timeout 根据配置HambombLockProperties.lockTimeout
     * waitTimeout 根据配置HambombLockProperties.lockWaitTimeout
     * @param key lock关键字
     * @return
     */
    HambombLock create(String key);

    /**
     * 创建HambombLock
     * 不等待lock,第一次获取lock失败即获取失败。
     * @param key
     * @param timeout lock释放时间
     * @return
     */
    HambombLock create(String key, Duration timeout);

    /**
     * 创建HambombLock
     * @param key lock关键字
     * @param timeout lock释放时间
     * @param waitTimeout 等待获取lock时间
     * @return
     */
    HambombLock create(String key, Duration timeout, Duration waitTimeout);

    /**
     * 创建HambombLock,lock关键字为object.hashCode().
     * timeout 根据配置HambombLockProperties.lockTimeout
     * waitTimeout 根据配置HambombLockProperties.lockWaitTimeout
     * @param object 加锁对象
     * @return
     */
    HambombLock create(Object object);

    /**
     * 创建HambombLock,lock关键字为object.hashCode().
     * 不等待lock,第一次获取lock失败即获取失败。
     * @param object 加锁对象
     * @param timeout lock释放时间
     * @return
     */
    HambombLock create(Object object, Duration timeout);

    /**
     * 创建HambombLock,lock关键字为object.hashCode().
     * @param object 加锁对象
     * @param timeout lock释放时间
     * @param waitTimeout 等待获取lock时间
     * @return
     */
    HambombLock create(Object object, Duration timeout, Duration waitTimeout);

    /**
     * 创建HambombLock,lock关键字为Lockable.run(object)的结果.
     * timeout 根据配置HambombLockProperties.lockTimeout
     * waitTimeout 根据配置HambombLockProperties.lockWaitTimeout
     * @param object 加锁对象
     * @param lockable
     * @return
     */
    HambombLock create(Object object, Lockable lockable);

    /**
     * 创建HambombLock,lock关键字为Lockable.run(object)的结果.
     * timeout 根据配置HambombLockProperties.lockTimeout
     * waitTimeout 不等待lock释放
     * @param object 加锁对象
     * @param lockable 计算key的方法
     * @param timeout lock释放时间
     * @return
     */
    HambombLock create(Object object, Lockable lockable, Duration timeout);

    /**
     * 创建HambombLock,lock关键字为Lockable.run(object)的结果.
     * @param object 加锁对象
     * @param lockable 计算key的方法
     * @param timeout lock释放时间
     * @param waitTimeout 等待锁的时间
     * @return
     */
    HambombLock create(Object object, Lockable lockable, Duration timeout, Duration waitTimeout);
}
