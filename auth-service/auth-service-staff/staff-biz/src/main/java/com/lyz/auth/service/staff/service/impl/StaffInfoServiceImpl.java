package com.lyz.auth.service.staff.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.staff.dao.StaffInfoMapper;
import com.lyz.auth.service.staff.model.StaffInfoDO;
import com.lyz.auth.service.staff.service.IStaffInfoService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:09
 */
@Service
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfoDO> implements IStaffInfoService {
}
