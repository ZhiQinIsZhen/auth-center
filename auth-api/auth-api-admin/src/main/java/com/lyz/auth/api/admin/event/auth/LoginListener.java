package com.lyz.auth.api.admin.event.auth;

import com.lyz.auth.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:36
 */
@Slf4j
@Service
public class LoginListener implements ApplicationListener<LoginEvent> {

    @Override
    public void onApplicationEvent(LoginEvent event) {
        log.info("用户Id:[{}], 用户名:[{}], 时间:[{}], {}系统了...",
                event.getStaffId(),
                event.getStaffName(),
                DateUtil.formatDate(new Date(event.getTimestamp())),
                event.isLogin() ? "登录" : "登出"
        );
    }
}
