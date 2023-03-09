package com.lyz.auth.common.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 10:35
 */
@Getter
@Setter
public class BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页码")
    private Long pageNum = 1L;

    @ApiModelProperty(value = "每页数量")
    private Long pageSize = 10L;
}
