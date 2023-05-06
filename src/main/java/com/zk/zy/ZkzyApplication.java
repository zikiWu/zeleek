package com.zk.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.zk.zy", exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.zk.zy.mapper")
@EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy=true)
@EnableScheduling
@EnableTransactionManagement
public class ZkzyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkzyApplication.class, args);
    }

}
