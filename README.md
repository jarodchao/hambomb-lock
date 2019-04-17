# hambomb-lock
* Java开发基于redis或者zookeeper的分布式锁
* 支持spring boot自动配置
## Quick Start
### 配置hambomb-lock
* maven pom
```$xslt
<dependency>
   <groupId>org.hambomb.lock</groupId>
   <artifactId>hambomb-lock</artifactId>
   <version>1.0.1</version>
</dependency>

<!-- Reids -->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<dependency>
   <groupId>org.apache.commons</groupId>
   <artifactId>commons-pool2</artifactId>
</dependency>

<!-- Zookeeper --> 
<dependency>
  <groupId>com.github.sgroschupf</groupId>
  <artifactId>zkclient</artifactId>
  <version>0.1</version>
</dependency>   
```
*在spring boot启动类上使用@EnableHambombLock即可开启hambomb-lock
### 使用redis
* applicaiton.yml
```$xslt
spring:
  redis:
    host: localhost
    port: 6379
    database: 0
    lettuce:
      pool:
        max-active: 8
        max-wait: 10000
        max-idle: 8
        min-idle: 0
      shutdown-timeout: 100
      
hambomb:
  lock:
    agentStrategy: redis
    lock-timeout: 500
    lock-wait-timeout: 500
```
### 使用Zookeeper
* applicaiton.yml
```$xslt
hambomb:
  lock:
    agentStrategy: zookeeper
    lock-timeout: 500
    lock-wait-timeout: 500
```
### 使用例子
```$xslt
@Service
public class ZookeeperLockService {

    @Autowired
    private HambombLockFactory hambombLockFactory;

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperLockService.class);

    public void modifyPhone() {
        HambombLock lock = hambombLockFactory.create("HUAWEI");

        if (lock.lock()) {


            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                lock.unlock();
            }
        }

    }
}
```
**==更多例子见examples工程。==**

