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

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-11
 */
public class ZookeeperHambombLockImpl implements HambombLock {

    protected String key;

    protected Duration timeout;

    protected Duration waitTimeout;

    private String lockPath;

    private String CURRENT_LOCK;

    private ZkClient zkClient;

    public static final String rootPath = "/Hambomb";
    public static final String projectPath = "/Lock";
    private static final String suffix = "/locks_";

    public ZookeeperHambombLockImpl(String key, Duration timeout, Duration waitTimeout, ZkClient zkClient) {
        this.key = key;
        this.timeout = timeout;
        this.waitTimeout = waitTimeout;
        this.zkClient = zkClient;

        this.lockPath = rootPath + projectPath + "/" + key;

        if (!zkClient.exists(lockPath)) {
            zkClient.createPersistent(lockPath);
        }
    }

    @Override
    public boolean lock() {

        if (tryLock()) {

            LockListener lockListener = new LockListener(zkClient);
            lockListener.watch(CURRENT_LOCK, timeout);

            return true;
        } else if (waitTimeout != null && !waitTimeout.isZero()) {
            return waitLock();
        }

        return false;

    }

    @Override
    public boolean unlock() {

        if (zkClient.exists(CURRENT_LOCK)) {
            zkClient.delete(CURRENT_LOCK);
        }

        return true;
    }

    private boolean tryLock() {

        CURRENT_LOCK = zkClient.createEphemeralSequential(getLockNodePath(key), null);

        return isLocked();

    }

    private boolean waitLock() {

        Timer timer = new Timer(waitTimeout);

        timer.watch();

        return isLocked();

    }

    private boolean isLocked() {
        List<String> children = zkClient.getChildren(lockPath);

        List<String> locks = new ArrayList<>(children.size());


        children.stream().forEach(x -> locks.add(x.split("_")[1]));

        Collections.sort(locks);

        String current_node = CURRENT_LOCK.split("_")[1];

        if (current_node.equals(locks.get(0))) {
            return true;
        }

        return false;
    }

    private String getLockNodePath(String key) {

        StringBuilder stringBuilder = new StringBuilder(rootPath);
        stringBuilder.append(projectPath);
        stringBuilder.append("/");
        stringBuilder.append(key);
        stringBuilder.append(suffix);

        return stringBuilder.toString();
    }

    private static class LockListener {

        private ZkClient zkClient;

        public LockListener(ZkClient zkClient) {
            this.zkClient = zkClient;
        }

        public void watch(String node, Duration timeout) {

            Timer timer = new Timer(timeout);

            Thread thread = new Thread(() -> {
                timer.watch();

                if (zkClient.exists(node)) {
                    zkClient.delete(node);
                }
            });

            thread.start();

        }

    }

}
