package com.zk.leek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@MapperScan("com.zk.leek.mapper")
@EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy=true)
@EnableScheduling
@EnableTransactionManagement
public class ZkleekApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkleekApplication.class, args);
    }

}
