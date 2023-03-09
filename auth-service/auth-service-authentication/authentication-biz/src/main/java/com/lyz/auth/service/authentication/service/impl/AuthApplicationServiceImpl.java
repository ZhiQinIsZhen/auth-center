package com.lyz.auth.service.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.authentication.dao.AuthApplicationMapper;
import com.lyz.auth.service.authentication.moedel.AuthApplicationDO;
import com.lyz.auth.service.authentication.service.IAuthApplicationService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:47
 */
@Service
public class AuthApplicationServiceImpl extends ServiceImpl<AuthApplicationMapper, AuthApplicationDO> implements IAuthApplicationService {

}
