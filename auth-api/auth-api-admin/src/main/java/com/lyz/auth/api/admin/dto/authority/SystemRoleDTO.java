package com.lyz.auth.api.admin.dto.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:23
 */
@Getter
@Setter
public class SystemRoleDTO implements Serializable {
    private static final long serialVersionUID = 2197798732348814224L;

    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @ApiModelProperty(value = "父角色ID", required = true)
    private Integer parentRoleId;

    @NotBlank(message = "应用名不能为空")
    @ApiModelProperty(value = "应用名", required = true)
    private String applicationName;
}
