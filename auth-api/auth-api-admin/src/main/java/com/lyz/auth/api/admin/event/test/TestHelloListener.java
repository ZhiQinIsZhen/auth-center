package com.lyz.auth.api.admin.event.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 11:23
 */
@Slf4j
@Service
public class TestHelloListener implements ApplicationListener<TestHelloEvent> {

    @Async
    @Override
    public void onApplicationEvent(TestHelloEvent event) {
        log.info("开始测试了， msg : {}", event.getMsg());
    }
}
