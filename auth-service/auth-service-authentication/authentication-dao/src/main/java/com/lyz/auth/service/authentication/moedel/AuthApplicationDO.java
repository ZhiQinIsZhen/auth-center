package com.lyz.auth.service.authentication.moedel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lyz.auth.common.dao.model.BaseDO;
import lombok.*;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:38
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth_application")
public class AuthApplicationDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = -5151397049526247715L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * dubbo组
     */
    private String dubboGroup;

    /**
     * dubbo超时时间
     */
    private Integer dubboTimeout;

    /**
     * dubbo版本号
     */
    private String dubboVersion;
}
