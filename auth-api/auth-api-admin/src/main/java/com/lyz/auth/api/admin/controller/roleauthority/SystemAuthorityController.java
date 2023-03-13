package com.lyz.auth.api.admin.controller.roleauthority;

import com.lyz.auth.api.admin.dto.authority.SystemAuthorityDTO;
import com.lyz.auth.api.admin.dto.authority.SystemRoleAuthorityDTO;
import com.lyz.auth.api.admin.dto.authority.SystemRoleDTO;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.service.staff.bo.SystemAuthorityBO;
import com.lyz.auth.service.staff.bo.SystemRoleAuthorityBO;
import com.lyz.auth.service.staff.bo.SystemRoleBO;
import com.lyz.auth.service.staff.remote.RemoteSystemAuthorityService;
import com.lyz.auth.service.staff.remote.RemoteSystemRoleAuthorityService;
import com.lyz.auth.service.staff.remote.RemoteSystemRoleService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 14:29
 */
@Api(tags = "系统权限信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败")
})
@Slf4j
@RestController
@RequestMapping("/system/authority")
public class SystemAuthorityController {

    @DubboReference
    private RemoteSystemAuthorityService remoteSystemAuthorityService;
    @DubboReference
    private RemoteSystemRoleService remoteSystemRoleService;
    @DubboReference
    private RemoteSystemRoleAuthorityService remoteSystemRoleAuthorityService;

    @PreAuthorize("hasAuthority('AUTH-API-ADMIN:SYSTEM-AUTHORITY-ADD'.toUpperCase())")
    @ApiOperation("增加一个系统权限码")
    @PostMapping("/addAuthority")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public Result<Boolean> addAuthority(@RequestBody @Validated SystemAuthorityDTO systemAuthorityDTO) {
        remoteSystemAuthorityService.addSystemAuthority(BeanUtil.copyProperties(systemAuthorityDTO, SystemAuthorityBO.class));
        return Result.success(Boolean.TRUE);
    }

    @PreAuthorize("hasAuthority('AUTH-API-ADMIN:SYSTEM-ROLE-ADD'.toUpperCase())")
    @ApiOperation("增加一个系统角色")
    @PostMapping("/addRole")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public Result<Boolean> addRole(@RequestBody @Validated SystemRoleDTO systemRoleDTO) {
        remoteSystemRoleService.addSystemRole(BeanUtil.copyProperties(systemRoleDTO, SystemRoleBO.class));
        return Result.success(Boolean.TRUE);
    }

    @PreAuthorize("hasAuthority('AUTH-API-ADMIN:STAFFINFO'.toUpperCase())")
    @ApiOperation("给某个权限绑定一个角色")
    @PostMapping("/bindRole")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public Result<Boolean> bindRole(@RequestBody @Validated SystemRoleAuthorityDTO systemRoleAuthorityDTO) {
        remoteSystemRoleAuthorityService.bindRole(BeanUtil.copyProperties(systemRoleAuthorityDTO, SystemRoleAuthorityBO.class));
        return Result.success(Boolean.TRUE);
    }
}
