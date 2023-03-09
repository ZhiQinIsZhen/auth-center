package com.lyz.auth.service.authentication.listener;

import com.lyz.auth.service.authentication.constant.Device;
import com.lyz.auth.service.authentication.remote.RemoteAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 16:23
 */
@Slf4j
@Service
public class InterfaceHotListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private RemoteAuthenticationService remoteAuthenticationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            remoteAuthenticationService.loadByUsername("auth-api-admin:15988654730", Device.WEB.getType());
            log.info("Dubbo GenericService hot success");
        } catch (Exception e) {
            log.warn("Dubbo GenericService hot fail");
        }
    }
}
