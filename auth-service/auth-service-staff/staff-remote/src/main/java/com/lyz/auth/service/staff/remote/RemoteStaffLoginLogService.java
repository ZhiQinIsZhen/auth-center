package com.lyz.auth.service.staff.remote;

import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.staff.bo.StaffLoginLogBO;

import javax.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:19
 */
public interface RemoteStaffLoginLogService {

    /**
     * 根据staffId分页查询登录日志
     *
     * @param staffId
     * @param pageBO
     * @return
     */
    RemotePage<StaffLoginLogBO> page(@NotNull Long staffId, @NotNull BasePageBO pageBO);
}
