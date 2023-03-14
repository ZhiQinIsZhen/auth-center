package com.lyz.auth.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.user.dao.UserLoginLogMapper;
import com.lyz.auth.service.user.model.UserLoginLogDO;
import com.lyz.auth.service.user.service.IUserLoginLogService;
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
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDO> implements IUserLoginLogService {

    /**
     * 获取上次登录时间
     *
     * @param userId
     * @param device
     * @return
     */
    @Override
    public Date lastLoginTime(Long userId, Integer device) {
        Page<UserLoginLogDO> page = page(
                new Page<>(1, 1),
                Wrappers.lambdaQuery(UserLoginLogDO.builder().userId(userId).device(device).build()).orderByDesc(UserLoginLogDO::getId)
        );
        Date lastLoginTime = null;
        if (Objects.nonNull(page) && !CollectionUtils.isEmpty(page.getRecords())) {
            lastLoginTime = page.getRecords().get(0).getLoginTime();
        }
        return lastLoginTime;
    }
}
