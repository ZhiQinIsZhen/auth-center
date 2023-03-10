package com.lyz.auth.service.authentication;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:36
 */
@EnableDubbo
@SpringBootApplication
public class AuthServiceAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceAuthenticationApplication.class, args);
    }
}
