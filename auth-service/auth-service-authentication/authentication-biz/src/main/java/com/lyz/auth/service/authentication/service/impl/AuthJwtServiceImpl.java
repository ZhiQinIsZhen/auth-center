package com.lyz.auth.service.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyz.auth.service.authentication.dao.AuthJwtMapper;
import com.lyz.auth.service.authentication.moedel.AuthJwtDO;
import com.lyz.auth.service.authentication.service.IAuthJwtService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:46
 */
@Service
public class AuthJwtServiceImpl extends ServiceImpl<AuthJwtMapper, AuthJwtDO> implements IAuthJwtService {
}
