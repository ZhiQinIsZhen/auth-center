package com.lyz.auth.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.service.staff.bo.SystemRoleAuthorityBO;
import com.lyz.auth.service.staff.model.SystemRoleAuthorityDO;
import com.lyz.auth.service.staff.remote.RemoteSystemRoleAuthorityService;
import com.lyz.auth.service.staff.service.ISystemRoleAuthorityService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 15:38
 */
@DubboService
public class RemoteSystemRoleAuthorityServiceImpl implements RemoteSystemRoleAuthorityService {

    @Resource
    private ISystemRoleAuthorityService systemRoleAuthorityService;

    /**
     * 给一个权限项绑定一个角色
     *
     * @param systemRoleAuthorityBO
     * @return
     */
    @Override
    public Boolean bindRole(SystemRoleAuthorityBO systemRoleAuthorityBO) {
        return systemRoleAuthorityService.save(BeanUtil.copyProperties(systemRoleAuthorityBO, SystemRoleAuthorityDO.class));
    }

    /**
     * 根据一个角色查询出权限项
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SystemRoleAuthorityBO> getByRoleId(Integer roleId) {
        return BeanUtil.copyProperties(systemRoleAuthorityService.list(Wrappers.query(SystemRoleAuthorityDO.builder().roleId(roleId).build())), SystemRoleAuthorityBO.class);
    }
}
