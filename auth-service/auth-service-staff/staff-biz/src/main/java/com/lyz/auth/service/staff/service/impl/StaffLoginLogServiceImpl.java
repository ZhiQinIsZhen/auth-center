package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.StaffLoginLogMapper;
import com.lyz.auth.service.staff.model.StaffLoginLogDO;
import com.lyz.auth.service.staff.service.IStaffLoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:12
 */
@Service
public class StaffLoginLogServiceImpl extends ServiceImpl<StaffLoginLogMapper, StaffLoginLogDO> implements IStaffLoginLogService {

    /**
     * 获取上次登录时间
     *
     * @param staffId
     * @param device
     * @return
     */
    @Override
    public Date lastLoginTime(Long staffId, Integer device) {
        Page<StaffLoginLogDO> page = page(
                new Page<>(1, 1),
                Wrappers.lambdaQuery(StaffLoginLogDO.builder().staffId(staffId).device(device).build()).orderByDesc(StaffLoginLogDO::getId)
        );
        Date lastLoginTime = null;
        if (Objects.nonNull(page) && !CollectionUtils.isEmpty(page.getRecords())) {
            lastLoginTime = page.getRecords().get(0).getLoginTime();
        }
        return lastLoginTime;
    }
}
