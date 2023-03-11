package com.lyz.auth.service.staff.remote;

import com.lyz.auth.service.staff.bo.SystemRoleBO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:21
 */
public interface RemoteSystemRoleService {

    /**
     * 创建一个角色
     *
     * @param systemRoleBO
     * @return
     */
    SystemRoleBO addSystemRole(@NotNull SystemRoleBO systemRoleBO);

    /**
     * 查询角色列表
     *
     * @return
     */
    List<SystemRoleBO> list();
}
