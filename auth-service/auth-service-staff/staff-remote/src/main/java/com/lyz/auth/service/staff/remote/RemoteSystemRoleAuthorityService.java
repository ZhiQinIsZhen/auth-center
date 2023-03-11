package com.lyz.auth.service.staff.remote;

import com.lyz.auth.service.staff.bo.SystemRoleAuthorityBO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 15:35
 */
public interface RemoteSystemRoleAuthorityService {

    /**
     * 给一个权限项绑定一个角色
     *
     * @param systemRoleAuthorityBO
     * @return
     */
    Boolean bindRole(@NotNull SystemRoleAuthorityBO systemRoleAuthorityBO);

    /**
     * 根据一个角色查询出权限项
     *
     * @param roleId
     * @return
     */
    List<SystemRoleAuthorityBO> getByRoleId(@NotNull Integer roleId);
}
