package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.authentication.constant.LoginType;
import com.lyz.auth.service.staff.dao.StaffAuthMobileMapper;
import com.lyz.auth.service.staff.model.StaffAuthMobileDO;
import com.lyz.auth.service.staff.model.base.StaffAuthBaseDO;
import com.lyz.auth.service.staff.service.IStaffAuthMobileService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:07
 */
@Service
public class StaffAuthMobileServiceImpl extends ServiceImpl<StaffAuthMobileMapper, StaffAuthMobileDO> implements IStaffAuthMobileService {

    @Override
    public StaffAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(StaffAuthMobileDO.builder().mobile(username).build()));
    }

    @Override
    public LoginType loginType() {
        return LoginType.MOBILE;
    }
}
