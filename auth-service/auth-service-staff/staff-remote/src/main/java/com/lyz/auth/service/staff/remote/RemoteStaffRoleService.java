package com.lyz.auth.service.staff.remote;

import com.lyz.auth.service.staff.bo.StaffRoleBO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 13:40
 */
public interface RemoteStaffRoleService {

    /**
     * 给员工绑定一个角色
     *
     * @param staffRoleBO
     * @return
     */
    StaffRoleBO bindRole(@NotNull StaffRoleBO staffRoleBO);

    /**
     * 查询员工拥有的角色
     *
     * @param staffId
     * @return
     */
    List<StaffRoleBO> listByStaffId(@NotNull Long staffId);
}
