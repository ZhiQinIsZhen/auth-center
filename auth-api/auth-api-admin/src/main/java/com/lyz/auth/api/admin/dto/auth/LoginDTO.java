package com.lyz.auth.api.admin.dto.auth;

import com.lyz.auth.common.controller.annotation.Trim;
import com.lyz.auth.common.util.PatternUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:24
 */
@Getter
@Setter
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = -4503927168128475109L;

    @Trim
    @ApiModelProperty(value = "登录名", example = "159887859", required = true)
    @NotBlank(message = "登录名不能为空", groups = {Login.class})
    private String loginName;

    @Trim
    @ApiModelProperty(value = "密码，8-20位数字或字母组成", example = "123456789", required = true)
    @Pattern(regexp = PatternUtil.PASSWORD_STRONG, groups = {Login.class}, message = "请输入8到20位数字和字母组合")
    private String loginPwd;

    @ApiModelProperty(value = "重定向路劲")
    private String redirect;

    public interface Login {}
}
