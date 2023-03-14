package com.lyz.auth.service.user.remote;

import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.user.bo.UserInfoBO;

import javax.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:16
 */
public interface RemoteUserInfoService {

    /**
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfoBO getByUserId(@NotNull Long userId);

    /**
     * 分页查询客户信息
     *
     * @param pageBO
     * @return
     */
    RemotePage<UserInfoBO> page(@NotNull BasePageBO pageBO);
}
