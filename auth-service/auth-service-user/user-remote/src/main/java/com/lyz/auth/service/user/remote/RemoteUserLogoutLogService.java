package com.lyz.auth.service.user.remote;

import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.user.bo.UserLogoutLogBO;

import javax.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:21
 */
public interface RemoteUserLogoutLogService {

    /**
     * 根据userId分页查询登出日志
     *
     * @param userId
     * @param pageBO
     * @return
     */
    RemotePage<UserLogoutLogBO> page(@NotNull Long userId, @NotNull BasePageBO pageBO);
}
