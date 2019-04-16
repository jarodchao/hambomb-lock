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

/**
 * 可加锁对象锁key计算接口
 * @author: <a herf="matilto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-10
 */
@FunctionalInterface
public interface Lockable {


    /**
     * 计算对象加锁的key值
     * @param object
     * @return
     */
    String run(Object object);

}
