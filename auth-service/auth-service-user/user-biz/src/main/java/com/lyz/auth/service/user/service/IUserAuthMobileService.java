package com.lyz.auth.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyz.auth.service.user.model.UserAuthMobileDO;
import com.lyz.auth.service.user.strategy.LoginTypeService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:07
 */
public interface IUserAuthMobileService extends IService<UserAuthMobileDO>, LoginTypeService {
}
