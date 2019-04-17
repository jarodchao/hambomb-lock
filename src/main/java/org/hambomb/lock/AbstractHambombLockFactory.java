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

import org.hambomb.lock.autoconfigure.HambombLockProperties;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-04-17
 */
public abstract class AbstractHambombLockFactory implements HambombLockFactory {

    private HambombLockProperties hambombLockProperties;

    public AbstractHambombLockFactory(HambombLockProperties hambombLockProperties) {
        this.hambombLockProperties = hambombLockProperties;
    }

    @Override
    abstract public HambombLock create(String key, Duration timeout, Duration waitTimeout);

    @Override
    public HambombLock create(Object object, Duration timeout, Duration waitTimeout) {
        return create(String.valueOf(object.hashCode()), timeout, waitTimeout);
    }

    @Override
    public HambombLock create(Object object, Lockable lockable, Duration timeout, Duration waitTimeout) {
        return create(lockable.run(object), timeout, waitTimeout);
    }

    @Override
    public HambombLock create(String key) {
        return create(key,
                Duration.of(hambombLockProperties.getLockTimeout(), ChronoUnit.MILLIS),
                Duration.of(hambombLockProperties.getLockWaitTimeout(), ChronoUnit.MILLIS));
    }

    @Override
    public HambombLock create(Object object) {
        return create(String.valueOf(object.hashCode()),
                Duration.of(hambombLockProperties.getLockTimeout(), ChronoUnit.MILLIS),
                Duration.of(hambombLockProperties.getLockWaitTimeout(), ChronoUnit.MILLIS));
    }

    @Override
    public HambombLock create(Object object, Lockable lockable) {
        return create(lockable.run(object),
                Duration.of(hambombLockProperties.getLockTimeout(), ChronoUnit.MILLIS),
                Duration.of(hambombLockProperties.getLockWaitTimeout(), ChronoUnit.MILLIS));
    }

    @Override
    public HambombLock create(String key, Duration timeout) {
        return create(key, timeout, null);
    }

    @Override
    public HambombLock create(Object object, Duration timeout) {
        return create(String.valueOf(object.hashCode()), timeout, null);
    }

    @Override
    public HambombLock create(Object object, Lockable lockable, Duration timeout) {
        return create(lockable.run(object), timeout, null);
    }

    protected Duration getTimeout(Duration timeout) {

        Assert.isTrue(timeout == null && hambombLockProperties.getLockTimeout() == -1 ? false : true
                ,"Timeout and HambombLockProperties.lockTimeout " +
                    "cannot be empty or undefined at the same time. ");

        if (timeout != null && !timeout.isZero()) {
            return timeout;
        }else {
            return Duration.of(hambombLockProperties.getLockTimeout(), ChronoUnit.MILLIS);
        }
    }

    protected Duration getWaitTimeout(Duration waitTimeout) {

//        Assert.isTrue(waitTimeout == null && hambombLockProperties.getLockWaitTimeout() == -1 ? false : true,
//                "WaitTimeout and HambombLockProperties.lockTimeout " +
//                "cannot be empty or undefined at the same time. ");

        if (waitTimeout != null && !waitTimeout.isZero()) {
            return waitTimeout;
        }else {

            if (hambombLockProperties.getLockWaitTimeout() == -1) {
                return null;
            } else {

                return Duration.of(hambombLockProperties.getLockTimeout(), ChronoUnit.MILLIS);
            }
        }
    }
}
