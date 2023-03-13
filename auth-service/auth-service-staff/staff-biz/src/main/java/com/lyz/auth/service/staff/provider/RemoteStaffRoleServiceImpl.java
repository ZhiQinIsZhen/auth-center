package com.lyz.auth.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.service.staff.bo.StaffRoleBO;
import com.lyz.auth.service.staff.model.StaffRoleDO;
import com.lyz.auth.service.staff.remote.RemoteStaffRoleService;
import com.lyz.auth.service.staff.service.IStaffRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 13:44
 */
@DubboService
public class RemoteStaffRoleServiceImpl implements RemoteStaffRoleService {

    @Resource
    private IStaffRoleService staffRoleService;

    /**
     * 给员工绑定一个角色
     *
     * @param staffRoleBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StaffRoleBO bindRole(StaffRoleBO staffRoleBO) {
        staffRoleService.save(BeanUtil.copyProperties(staffRoleBO, StaffRoleDO.class));
        return staffRoleBO;
    }

    /**
     * 查询员工拥有的角色
     *
     * @param staffId
     * @return
     */
    @Override
    public List<StaffRoleBO> listByStaffId(Long staffId) {
        return BeanUtil.copyProperties(staffRoleService.list(Wrappers.query(StaffRoleDO.builder().staffId(staffId).build())), StaffRoleBO.class);
    }
}
