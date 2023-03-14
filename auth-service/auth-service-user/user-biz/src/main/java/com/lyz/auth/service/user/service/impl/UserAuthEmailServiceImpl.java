package com.lyz.auth.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.authentication.constant.LoginType;
import com.lyz.auth.service.user.dao.UserAuthEmailMapper;
import com.lyz.auth.service.user.model.UserAuthEmailDO;
import com.lyz.auth.service.user.model.base.UserAuthBaseDO;
import com.lyz.auth.service.user.service.IUserAuthEmailService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:05
 */
@Service
public class UserAuthEmailServiceImpl extends ServiceImpl<UserAuthEmailMapper, UserAuthEmailDO> implements IUserAuthEmailService {

    @Override
    public UserAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(UserAuthEmailDO.builder().email(username).build()));
    }

    @Override
    public LoginType loginType() {
        return LoginType.EMAIL;
    }
}
