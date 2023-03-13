package com.lyz.auth.api.admin.controller.roleauthority;

import com.lyz.auth.api.admin.dto.authority.StaffRoleDTO;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.security.client.context.AuthContext;
import com.lyz.auth.service.authentication.bo.AuthUserBO;
import com.lyz.auth.service.staff.bo.StaffRoleBO;
import com.lyz.auth.service.staff.remote.RemoteStaffRoleService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 14:45
 */
@Api(tags = "员工权限信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败")
})
@Slf4j
@RestController
@RequestMapping("/staff/authority")
public class StaffAuthorityController {

    @DubboReference
    private RemoteStaffRoleService remoteStaffRoleService;

    @PreAuthorize("hasAuthority('AUTH-API-ADMIN:STAFF-BIND-ROLE'.toUpperCase())")
    @ApiOperation("给员工绑定一个角色")
    @PostMapping("/bindRole")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public Result<Boolean> bindRole(@RequestBody @Validated StaffRoleDTO staffRoleDTO) {
        AuthUserBO authUser = AuthContext.getAuthUser();
        staffRoleDTO.setStaffId(Objects.nonNull(staffRoleDTO.getStaffId()) ? staffRoleDTO.getStaffId() : authUser.getUserId());
        remoteStaffRoleService.bindRole(BeanUtil.copyProperties(staffRoleDTO, StaffRoleBO.class));
        return Result.success(Boolean.TRUE);
    }
}
