package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.SystemAuthorityMapper;
import com.lyz.auth.service.staff.model.SystemAuthorityDO;
import com.lyz.auth.service.staff.service.ISystemAuthorityService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 11:51
 */
@Service
public class SystemAuthorityServiceImpl extends ServiceImpl<SystemAuthorityMapper, SystemAuthorityDO> implements ISystemAuthorityService {
}
