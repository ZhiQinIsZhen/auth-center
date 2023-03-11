package com.lyz.auth.service.staff.provider;

import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.service.staff.bo.SystemAuthorityBO;
import com.lyz.auth.service.staff.model.SystemAuthorityDO;
import com.lyz.auth.service.staff.remote.RemoteSystemAuthorityService;
import com.lyz.auth.service.staff.service.ISystemAuthorityService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 15:30
 */
@DubboService
public class RemoteSystemAuthorityServiceImpl implements RemoteSystemAuthorityService {

    @Resource
    private ISystemAuthorityService systemAuthorityService;

    /**
     * 新增一个权限项
     *
     * @param systemAuthorityBO
     * @return
     */
    @Override
    public SystemAuthorityBO addSystemAuthority(SystemAuthorityBO systemAuthorityBO) {
        systemAuthorityService.save(BeanUtil.copyProperties(systemAuthorityBO, SystemAuthorityDO.class));
        return systemAuthorityBO;
    }

    /**
     * 查询权限项列表
     *
     * @return
     */
    @Override
    public List<SystemAuthorityBO> list() {
        return BeanUtil.copyProperties(systemAuthorityService.list(), SystemAuthorityBO.class);
    }
}
