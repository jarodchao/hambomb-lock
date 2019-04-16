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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-16
 */
public class ZookeeperHandler implements AgentHandler {


    private ZkClient zkClient;

    private static final String rootPath = "/Hambomb";
    private static final String projectPath = "/Lock";
    private static final String suffix = "/locks_";

    public ZookeeperHandler(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public Locked lock(String key) {

        String currentLock = zkClient.createEphemeralSequential(getLockNodePath(key), null);

        List<String> children = zkClient.getChildren(rootPath + projectPath);

        List<String> locks = new ArrayList<>(children.size());

        return new Locked(true, Optional.of(currentLock));
    }

    @Override
    public boolean unlock(String key) {
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
}
