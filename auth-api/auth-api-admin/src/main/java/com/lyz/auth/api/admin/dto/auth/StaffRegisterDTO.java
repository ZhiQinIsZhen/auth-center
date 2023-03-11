package com.lyz.auth.api.admin.dto.auth;

import com.lyz.auth.common.util.PatternUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:19
 */
@Getter
@Setter
public class StaffRegisterDTO implements Serializable {
    private static final long serialVersionUID = -8173212674441564883L;

    @ApiModelProperty(value = "用户真实名称")
    private String realName;

    @ApiModelProperty(value = "用户昵称", required = true)
    @NotBlank(groups = {Register.class}, message = "请输入用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户手机号码")
    @Pattern(regexp = PatternUtil.PHONE_REG, groups = {Register.class}, message = "请输入正确的手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱地址")
    @Pattern(regexp = PatternUtil.EMAIL_REG, groups = {Register.class}, message = "请输入正确的邮箱地址")
    private String email;

    @ApiModelProperty(value = "密码，8-20位数字或字母组成", required = true)
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$", groups = {Register.class}, message = "请输入8到20位数字和字母组合")
    private String password;

    @NotBlank(groups = {Register.class}, message = "请输入注册的手机号码或邮箱地址")
    private String getLoginName() {
        return StringUtils.isNotBlank(mobile) ? mobile : email;
    }

    public interface Register{}
}
