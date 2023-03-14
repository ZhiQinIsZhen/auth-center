package com.lyz.auth.service.user.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.user.bo.UserLogoutLogBO;
import com.lyz.auth.service.user.model.UserLogoutLogDO;
import com.lyz.auth.service.user.remote.RemoteUserLogoutLogService;
import com.lyz.auth.service.user.service.IUserLogoutLogService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 11:05
 */
@DubboService
public class RemoteUserLogoutLogServiceImpl implements RemoteUserLogoutLogService {

    @Resource
    private IUserLogoutLogService userLogoutLogService;

    /**
     * 根据userId分页查询登出日志
     *
     * @param userId
     * @param pageBO
     * @return
     */
    @Override
    public RemotePage<UserLogoutLogBO> page(Long userId, BasePageBO pageBO) {
        Page page = userLogoutLogService.page(
                Page.of(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(UserLogoutLogDO.builder().userId(userId).build())
        );
        return BeanUtil.copyProperties(page, UserLogoutLogBO.class);
    }
}
