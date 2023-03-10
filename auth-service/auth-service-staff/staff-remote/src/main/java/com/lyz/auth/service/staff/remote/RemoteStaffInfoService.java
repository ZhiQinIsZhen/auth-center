package com.lyz.auth.service.staff.remote;

import com.lyz.auth.service.staff.bo.StaffInfoBO;

import javax.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:16
 */
public interface RemoteStaffInfoService {

    /**
     * 根据staffId获取用户信息
     *
     * @param staffId
     * @return
     */
    StaffInfoBO getByStaffId(@NotNull Long staffId);
}
