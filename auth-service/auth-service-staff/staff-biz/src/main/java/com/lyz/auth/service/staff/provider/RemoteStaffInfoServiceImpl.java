package com.lyz.auth.service.staff.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.staff.bo.StaffInfoBO;
import com.lyz.auth.service.staff.remote.RemoteStaffInfoService;
import com.lyz.auth.service.staff.service.IStaffInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:48
 */
@DubboService
public class RemoteStaffInfoServiceImpl implements RemoteStaffInfoService {

    @Resource
    private IStaffInfoService staffInfoService;

    /**
     * 根据staffId获取用户信息
     *
     * @param staffId
     * @return
     */
    @Override
    public StaffInfoBO getByStaffId(Long staffId) {
        return BeanUtil.copyProperties(staffInfoService.getById(staffId), StaffInfoBO.class);
    }

    /**
     * 分页查询员工信息
     *
     * @param pageBO
     * @return
     */
    @Override
    public RemotePage<StaffInfoBO> page(BasePageBO pageBO) {
        Page page = staffInfoService.page(Page.of(pageBO.getPageNum(), pageBO.getPageSize()));
        return BeanUtil.copyProperties(page, StaffInfoBO.class);
    }
}
