package com.lyz.auth.api.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Desc:main
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 10:51
 */
@EnableAsync
@SpringBootApplication
public class AuthApiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApiAdminApplication.class, args);
    }
}
