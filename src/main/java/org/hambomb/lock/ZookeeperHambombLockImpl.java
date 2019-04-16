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

import java.time.Duration;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-11
 */
public class ZookeeperHambombLockImpl implements HambombLock {

    protected String key;

    protected Duration timeout;

    private String CURRENT_LOCK;

    private ZkClient zkClient;

    public ZookeeperHambombLockImpl(String key, Duration timeout, ZkClient zkClient) {
        this.key = key;
        this.timeout = timeout;
        this.zkClient = zkClient;
    }

    @Override
    public boolean lock() {

        System.out.println("ZookeeperHambombLockImpl lock");

        return true;
    }

    @Override
    public boolean unlock() {

        System.out.println("ZookeeperHambombLockImpl unlock");

        return true;
    }
}
