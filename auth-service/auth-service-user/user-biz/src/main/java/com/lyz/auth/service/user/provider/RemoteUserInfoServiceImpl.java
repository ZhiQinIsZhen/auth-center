package com.lyz.auth.service.user.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.service.user.bo.UserInfoBO;
import com.lyz.auth.service.user.remote.RemoteUserInfoService;
import com.lyz.auth.service.user.service.IUserInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:48
 */
@DubboService
public class RemoteUserInfoServiceImpl implements RemoteUserInfoService {

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoBO getByUserId(Long userId) {
        return BeanUtil.copyProperties(userInfoService.getById(userId), UserInfoBO.class);
    }

    /**
     * 分页查询员工信息
     *
     * @param pageBO
     * @return
     */
    @Override
    public RemotePage<UserInfoBO> page(BasePageBO pageBO) {
        Page page = userInfoService.page(Page.of(pageBO.getPageNum(), pageBO.getPageSize()));
        return BeanUtil.copyProperties(page, UserInfoBO.class);
    }
}
