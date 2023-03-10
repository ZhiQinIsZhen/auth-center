package com.lyz.auth.service.staff.remote;

import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.staff.bo.StaffLogoutLogBO;

import javax.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:21
 */
public interface RemoteStaffLogoutLogService {

    /**
     * 根据staffId分页查询登出日志
     *
     * @param staffId
     * @param pageBO
     * @return
     */
    RemotePage<StaffLogoutLogBO> page(@NotNull Long staffId, @NotNull BasePageBO pageBO);
}
