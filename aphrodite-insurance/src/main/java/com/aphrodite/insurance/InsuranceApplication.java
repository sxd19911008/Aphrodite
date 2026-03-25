package com.aphrodite.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 保险服务启动类
 *
 * @author Aphrodite
 */
@SpringBootApplication(scanBasePackages = "com.aphrodite")
@EnableDiscoveryClient
public class InsuranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceApplication.class, args);
    }
}

