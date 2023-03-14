package com.lyz.auth.service.staff.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyz.auth.service.staff.model.StaffAuthMobileDO;
import com.lyz.auth.service.staff.strategy.LoginTypeService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:07
 */
public interface IStaffAuthMobileService extends IService<StaffAuthMobileDO>, LoginTypeService {
}
