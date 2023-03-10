package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.StaffLogoutLogMapper;
import com.lyz.auth.service.staff.model.StaffLogoutLogDO;
import com.lyz.auth.service.staff.service.IStaffLogoutLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:14
 */
@Service
public class StaffLogoutLogServiceImpl extends ServiceImpl<StaffLogoutLogMapper, StaffLogoutLogDO> implements IStaffLogoutLogService {

    /**
     * 获取上次登出时间
     *
     * @param staffId
     * @param device
     * @return
     */
    @Override
    public Date lastLogoutTime(Long staffId, Integer device) {
        Page<StaffLogoutLogDO> page = page(
                new Page<>(1, 1),
                Wrappers.lambdaQuery(StaffLogoutLogDO.builder().staffId(staffId).device(device).build()).orderByDesc(StaffLogoutLogDO::getId)
        );
        Date lastLogoutTime = null;
        if (Objects.nonNull(page) && !CollectionUtils.isEmpty(page.getRecords())) {
            lastLogoutTime = page.getRecords().get(0).getLogoutTime();
        }
        return lastLogoutTime;
    }
}
