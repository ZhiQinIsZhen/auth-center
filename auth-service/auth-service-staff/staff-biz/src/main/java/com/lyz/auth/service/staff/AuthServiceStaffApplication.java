package com.lyz.auth.service.staff;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:main
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:18
 */
@EnableDubbo
@SpringBootApplication
public class AuthServiceStaffApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceStaffApplication.class, args);
    }
}
