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

import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalTime;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-17
 */
public class Timer {

    private final LocalTime startTime = LocalTime.now();

    private Duration duration;

    public Timer(Duration duration) {
        this.duration = duration;
    }

    public boolean timer() {

        Assert.notNull(duration,"duration must be not null!");

        return Duration.between(startTime, LocalTime.now()).compareTo(duration) >= 0 ? true : false;
    }

    public void watch(){
        while (!timer()) {

        }
    }
}
