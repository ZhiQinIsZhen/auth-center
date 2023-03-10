package com.lyz.auth.service.staff.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyz.auth.service.staff.model.StaffLoginLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:12
 */
public interface IStaffLoginLogService extends IService<StaffLoginLogDO> {

    /**
     * 获取上次登录时间
     *
     * @param staffId
     * @param device
     * @return
     */
    Date lastLoginTime(Long staffId, Integer device);
}
