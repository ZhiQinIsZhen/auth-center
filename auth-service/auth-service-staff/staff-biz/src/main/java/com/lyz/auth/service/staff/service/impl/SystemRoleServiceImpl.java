package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.SystemRoleMapper;
import com.lyz.auth.service.staff.model.SystemRoleDO;
import com.lyz.auth.service.staff.service.ISystemRoleService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:20
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRoleDO> implements ISystemRoleService {
}
