package com.lyz.auth.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyz.auth.service.user.model.UserLoginLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:12
 */
public interface IUserLoginLogService extends IService<UserLoginLogDO> {

    /**
     * 获取上次登录时间
     *
     * @param userId
     * @param device
     * @return
     */
    Date lastLoginTime(Long userId, Integer device);
}
