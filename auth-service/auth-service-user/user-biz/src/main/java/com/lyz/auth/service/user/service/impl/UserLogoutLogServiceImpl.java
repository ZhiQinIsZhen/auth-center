package com.lyz.auth.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.user.dao.UserLogoutLogMapper;
import com.lyz.auth.service.user.model.UserLogoutLogDO;
import com.lyz.auth.service.user.service.IUserLogoutLogService;
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
public class UserLogoutLogServiceImpl extends ServiceImpl<UserLogoutLogMapper, UserLogoutLogDO> implements IUserLogoutLogService {

    /**
     * 获取上次登出时间
     *
     * @param userId
     * @param device
     * @return
     */
    @Override
    public Date lastLogoutTime(Long userId, Integer device) {
        Page<UserLogoutLogDO> page = page(
                new Page<>(1, 1),
                Wrappers.lambdaQuery(UserLogoutLogDO.builder().userId(userId).device(device).build()).orderByDesc(UserLogoutLogDO::getId)
        );
        Date lastLogoutTime = null;
        if (Objects.nonNull(page) && !CollectionUtils.isEmpty(page.getRecords())) {
            lastLogoutTime = page.getRecords().get(0).getLogoutTime();
        }
        return lastLogoutTime;
    }
}
