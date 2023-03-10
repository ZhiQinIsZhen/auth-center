package com.lyz.auth.service.staff.provider;

import com.lyz.auth.common.biz.util.BeanUtil;
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
}
