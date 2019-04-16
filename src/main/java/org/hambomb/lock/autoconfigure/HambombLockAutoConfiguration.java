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
package org.hambomb.lock.autoconfigure;

import org.I0Itec.zkclient.ZkClient;
import org.hambomb.lock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
@EnableConfigurationProperties(HambombLockProperties.class)
public class HambombLockAutoConfiguration {

    @Autowired
    private HambombLockProperties hambombLockProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "hambombLockFactory")
    @ConditionalOnProperty(name = "hambomb.lock.agentStrategy", havingValue = "zookeeper")
    public HambombLockFactory hambombLockFactoryForZK() {

        ZkClient zkClient = new ZkClient(hambombLockProperties.getZkUrl(), hambombLockProperties.getSessionTimeout(),
                hambombLockProperties.getConnectionTimeout());

        HambombLockFactory hambombLockFactory = new ZookeeperHambombLockFactory(zkClient);

        return hambombLockFactory;

    }

    @Bean(name = "hambombLockFactory")
    @ConditionalOnProperty(name = "hambomb.lock.agentStrategy", havingValue = "redis")
    public HambombLockFactory hambombLockFactoryForRedis() {

        RedisConnectionFactory factory = applicationContext.getBean(RedisConnectionFactory.class);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();

        HambombLockFactory hambombLockFactory = new RedisHambombLockFactory(redisTemplate);

        return hambombLockFactory;

    }

}
