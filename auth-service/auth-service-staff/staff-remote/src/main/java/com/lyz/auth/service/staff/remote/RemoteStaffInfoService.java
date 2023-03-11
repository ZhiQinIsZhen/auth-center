package com.lyz.auth.service.staff.remote;

import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.staff.bo.StaffInfoBO;
import com.lyz.auth.service.staff.bo.StaffLoginLogBO;

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

    /**
     * 分页查询员工信息
     *
     * @param pageBO
     * @return
     */
    RemotePage<StaffInfoBO> page(@NotNull BasePageBO pageBO);
}
