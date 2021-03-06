package org.hambomb.lock;

import org.hambomb.lock.autoconfigure.EnableHambombLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableHambombLock
public class AgentRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentRedisApplication.class, args);
    }

}
