package com.lyz.auth.service.staff.provider;

import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.service.staff.bo.SystemRoleBO;
import com.lyz.auth.service.staff.model.SystemRoleDO;
import com.lyz.auth.service.staff.remote.RemoteSystemRoleService;
import com.lyz.auth.service.staff.service.ISystemRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:26
 */
@DubboService
public class RemoteSystemRoleServiceImpl implements RemoteSystemRoleService {

    @Resource
    private ISystemRoleService systemRoleService;

    /**
     * 创建一个角色
     *
     * @param systemRoleBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemRoleBO addSystemRole(SystemRoleBO systemRoleBO) {
        systemRoleService.save(BeanUtil.copyProperties(systemRoleBO, SystemRoleDO.class));
        return systemRoleBO;
    }

    /**
     * 查询角色列表
     *
     * @return
     */
    @Override
    public List<SystemRoleBO> list() {
        return BeanUtil.copyProperties(systemRoleService.list(), SystemRoleBO.class);
    }
}
