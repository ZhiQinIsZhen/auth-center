package com.lyz.auth.api.admin.controller.staff;

import com.lyz.auth.api.admin.dto.staff.StaffLogPageDTO;
import com.lyz.auth.api.admin.vo.staff.StaffInfoVO;
import com.lyz.auth.api.admin.vo.staff.StaffLoginLogVO;
import com.lyz.auth.api.admin.vo.staff.StaffLogoutLogVO;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.controller.dto.BasePageDTO;
import com.lyz.auth.common.controller.result.PageResult;
import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.common.remote.page.BasePageBO;
import com.lyz.auth.common.remote.page.RemotePage;
import com.lyz.auth.security.client.context.AuthContext;
import com.lyz.auth.service.authentication.bo.AuthUserBO;
import com.lyz.auth.service.staff.bo.StaffInfoBO;
import com.lyz.auth.service.staff.bo.StaffLoginLogBO;
import com.lyz.auth.service.staff.bo.StaffLogoutLogBO;
import com.lyz.auth.service.staff.remote.RemoteStaffInfoService;
import com.lyz.auth.service.staff.remote.RemoteStaffLoginLogService;
import com.lyz.auth.service.staff.remote.RemoteStaffLogoutLogService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:39
 */
@Api(tags = "员工信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败")
})
@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffInfoController {

    @DubboReference
    private RemoteStaffInfoService remoteStaffInfoService;
    @DubboReference
    private RemoteStaffLoginLogService remoteStaffLoginLogService;
    @DubboReference
    private RemoteStaffLogoutLogService remoteStaffLogoutLogService;

    @PreAuthorize("hasAuthority('${spring.application.name}:STAFFINFO'.toUpperCase())")
    @ApiOperation("查询当前登录员工信息")
    @GetMapping("/current")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public Result<StaffInfoVO> userInfo() {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        return Result.success(BeanUtil.copyProperties(authUserBO, StaffInfoVO.class, (s, t) -> {
            t.setStaffId(authUserBO.getUserId());
        }));
    }

    @PreAuthorize("hasAuthority('${spring.application.name}:STAFFINFO'.toUpperCase())")
    @ApiOperation("分页查询员工信息")
    @GetMapping("/page")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public PageResult<StaffInfoVO> page(BasePageDTO page) {
        RemotePage<StaffInfoBO> remotePage = remoteStaffInfoService.page(BeanUtil.copyProperties(page, BasePageBO.class));
        return PageResult.success(BeanUtil.copyProperties(remotePage, StaffInfoVO.class));
    }

    @PreAuthorize("hasAuthority('${spring.application.name}:STAFFLOG'.toUpperCase())")
    @ApiOperation("分页查询员工登录日志")
    @GetMapping("/loginLogs/page")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public PageResult<StaffLoginLogVO> pageLoginLogs(StaffLogPageDTO page) {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        page = Objects.nonNull(page) ? page : new StaffLogPageDTO();
        page.setStaffId(Objects.nonNull(page.getStaffId()) ? page.getStaffId() : authUserBO.getUserId());
        RemotePage<StaffLoginLogBO> remotePage = remoteStaffLoginLogService.page(page.getStaffId(), BeanUtil.copyProperties(page, BasePageBO.class));
        return PageResult.success(BeanUtil.copyProperties(remotePage, StaffLoginLogVO.class));
    }

    @PreAuthorize("hasAuthority('${spring.application.name}:STAFFLOG'.toUpperCase())")
    @ApiOperation("分页查询员工登出日志")
    @GetMapping("/logoutLogs/page")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public PageResult<StaffLogoutLogVO> pageLogoutLogs(StaffLogPageDTO page) {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        page = Objects.nonNull(page) ? page : new StaffLogPageDTO();
        page.setStaffId(Objects.nonNull(page.getStaffId()) ? page.getStaffId() : authUserBO.getUserId());
        RemotePage<StaffLogoutLogBO> remotePage = remoteStaffLogoutLogService.page(page.getStaffId(), BeanUtil.copyProperties(page, BasePageBO.class));
        return PageResult.success(BeanUtil.copyProperties(remotePage, StaffLogoutLogVO.class));
    }
}
