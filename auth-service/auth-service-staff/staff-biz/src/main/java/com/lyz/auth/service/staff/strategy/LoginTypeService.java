package com.lyz.auth.service.staff.strategy;

import com.google.common.collect.Maps;
import com.lyz.auth.service.authentication.constant.LoginType;
import com.lyz.auth.service.staff.model.base.StaffAuthBaseDO;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/14 9:19
 */
public interface LoginTypeService extends InitializingBean {

    //容器
    Map<LoginType, LoginTypeService> LOGIN_TYPE_MAP = Maps.newEnumMap(LoginType.class);

    /**
     * init
     *
     * @throws Exception
     */
    @Override
    default void afterPropertiesSet() throws Exception {
        LOGIN_TYPE_MAP.put(loginType(), this);
    }

    /**
     * 获取认证信息
     *
     * @param username
     * @return
     */
    StaffAuthBaseDO getByUsername(String username);

    /**
     * 获取登录方式
     *
     * @return
     */
    LoginType loginType();
}
