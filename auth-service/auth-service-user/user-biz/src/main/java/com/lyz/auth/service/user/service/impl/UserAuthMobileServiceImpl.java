package com.lyz.auth.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.authentication.constant.LoginType;
import com.lyz.auth.service.user.dao.UserAuthMobileMapper;
import com.lyz.auth.service.user.model.UserAuthMobileDO;
import com.lyz.auth.service.user.model.base.UserAuthBaseDO;
import com.lyz.auth.service.user.service.IUserAuthMobileService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:07
 */
@Service
public class UserAuthMobileServiceImpl extends ServiceImpl<UserAuthMobileMapper, UserAuthMobileDO> implements IUserAuthMobileService {

    @Override
    public UserAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(UserAuthMobileDO.builder().mobile(username).build()));
    }

    @Override
    public LoginType loginType() {
        return LoginType.MOBILE;
    }
}
