package com.lyz.auth.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyz.auth.service.user.model.UserLogoutLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:14
 */
public interface IUserLogoutLogService extends IService<UserLogoutLogDO> {

    /**
     * 获取上次登出时间
     *
     * @param userId
     * @return
     */
    Date lastLogoutTime(Long userId, Integer device);
}
