package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.StaffAuthEmailMapper;
import com.lyz.auth.service.staff.model.StaffAuthEmailDO;
import com.lyz.auth.service.staff.service.IStaffAuthEmailService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:05
 */
@Service
public class StaffAuthEmailServiceImpl extends ServiceImpl<StaffAuthEmailMapper, StaffAuthEmailDO> implements IStaffAuthEmailService {
}
