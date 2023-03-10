package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.StaffAuthorityMapper;
import com.lyz.auth.service.staff.model.StaffAuthorityDO;
import com.lyz.auth.service.staff.service.IStaffAuthorityService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:08
 */
@Service
public class StaffAuthorityServiceImpl extends ServiceImpl<StaffAuthorityMapper, StaffAuthorityDO> implements IStaffAuthorityService {
}
