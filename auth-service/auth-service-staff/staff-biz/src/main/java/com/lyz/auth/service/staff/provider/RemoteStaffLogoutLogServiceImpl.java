package com.lyz.auth.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.staff.bo.StaffLogoutLogBO;
import com.lyz.auth.service.staff.model.StaffLogoutLogDO;
import com.lyz.auth.service.staff.remote.RemoteStaffLogoutLogService;
import com.lyz.auth.service.staff.service.IStaffLogoutLogService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 11:05
 */
@DubboService
public class RemoteStaffLogoutLogServiceImpl implements RemoteStaffLogoutLogService {

    @Resource
    private IStaffLogoutLogService staffLogoutLogService;

    /**
     * 根据staffId分页查询登出日志
     *
     * @param staffId
     * @param pageBO
     * @return
     */
    @Override
    public RemotePage<StaffLogoutLogBO> page(Long staffId, BasePageBO pageBO) {
        Page page = staffLogoutLogService.page(
                new Page<>(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(StaffLogoutLogDO.builder().staffId(staffId).build())
        );
        return BeanUtil.copyProperties(page, StaffLogoutLogBO.class);
    }
}
