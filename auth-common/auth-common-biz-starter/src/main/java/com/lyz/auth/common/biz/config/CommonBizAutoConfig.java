package com.lyz.auth.common.biz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:biz auto config
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 17:53
 */
@Slf4j
@Configuration
public class CommonBizAutoConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("biz auto config ...");
    }
}
