package com.lyz.auth.api.admin.dto.staff;

import com.lyz.auth.common.controller.dto.BasePageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 10:29
 */
@Getter
@Setter
public class StaffLogPageDTO extends BasePageDTO {

    @ApiModelProperty(value = "员工ID")
    private Long staffId;
}
