package com.lyz.auth.service.user.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.user.bo.UserLoginLogBO;
import com.lyz.auth.service.user.model.UserLoginLogDO;
import com.lyz.auth.service.user.remote.RemoteUserLoginLogService;
import com.lyz.auth.service.user.service.IUserLoginLogService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:50
 */
@DubboService
public class RemoteUserLoginLogServiceImpl implements RemoteUserLoginLogService {

    @Resource
    private IUserLoginLogService userLoginLogService;

    /**
     * 根据staffId分页查询登录日志
     *
     * @param userId
     * @param pageBO
     * @return
     */
    @Override
    public RemotePage<UserLoginLogBO> page(Long userId, BasePageBO pageBO) {
        Page page = userLoginLogService.page(
                Page.of(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(UserLoginLogDO.builder().userId(userId).build())
        );
        return BeanUtil.copyProperties(page, UserLoginLogBO.class);
    }
}
