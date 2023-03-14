package com.lyz.auth.service.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/14 13:59
 */
@EnableDubbo
@SpringBootApplication
public class AuthServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceUserApplication.class, args);
    }
}
