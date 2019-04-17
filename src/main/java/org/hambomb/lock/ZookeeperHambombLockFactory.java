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

import org.I0Itec.zkclient.ZkClient;
import org.hambomb.lock.autoconfigure.HambombLockProperties;
import org.springframework.beans.factory.InitializingBean;

import java.time.Duration;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
public class ZookeeperHambombLockFactory extends AbstractHambombLockFactory implements InitializingBean {

    private ZkClient zkClient;

    public ZookeeperHambombLockFactory(HambombLockProperties hambombLockProperties, ZkClient zkClient) {
        super(hambombLockProperties);
        this.zkClient = zkClient;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!zkClient.exists(ZookeeperHambombLockImpl.rootPath)) {
            zkClient.createPersistent(ZookeeperHambombLockImpl.rootPath);
        }

        if (!zkClient.exists(ZookeeperHambombLockImpl.rootPath + ZookeeperHambombLockImpl.projectPath)) {
            zkClient.createPersistent(ZookeeperHambombLockImpl.rootPath + ZookeeperHambombLockImpl.projectPath);
        }
    }

    @Override
    public HambombLock create(String key, Duration timeout, Duration waitTimeout) {
        return new ZookeeperHambombLockImpl(key, getTimeout(timeout), getWaitTimeout(waitTimeout), zkClient);
    }

}
