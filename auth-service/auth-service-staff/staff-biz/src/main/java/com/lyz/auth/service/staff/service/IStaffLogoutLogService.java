package com.lyz.auth.service.staff.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyz.auth.service.staff.model.StaffLogoutLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:14
 */
public interface IStaffLogoutLogService extends IService<StaffLogoutLogDO> {

    /**
     * 获取上次登出时间
     *
     * @param staffId
     * @return
     */
    Date lastLogoutTime(Long staffId, Integer device);
}
