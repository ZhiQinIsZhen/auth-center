package com.lyz.auth.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.staff.bo.StaffLoginLogBO;
import com.lyz.auth.service.staff.model.StaffLoginLogDO;
import com.lyz.auth.service.staff.remote.RemoteStaffLoginLogService;
import com.lyz.auth.service.staff.service.IStaffLoginLogService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:50
 */
@DubboService
public class RemoteStaffLoginLogServiceImpl implements RemoteStaffLoginLogService {

    @Resource
    private IStaffLoginLogService staffLoginLogService;

    /**
     * 根据staffId分页查询登陆日志
     *
     * @param staffId
     * @param pageBO
     * @return
     */
    @Override
    public RemotePage<StaffLoginLogBO> page(Long staffId, BasePageBO pageBO) {
        Page page = staffLoginLogService.page(
                new Page<>(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(StaffLoginLogDO.builder().staffId(staffId).build())
        );
        return BeanUtil.copyProperties(page, StaffLoginLogBO.class);
    }
}
