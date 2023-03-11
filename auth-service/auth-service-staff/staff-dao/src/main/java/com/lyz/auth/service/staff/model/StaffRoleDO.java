package com.lyz.auth.service.staff.model;

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
 * @date 2023/3/11 11:38
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("staff_role")
public class StaffRoleDO  extends BaseDO implements Serializable {
    private static final long serialVersionUID = -1600563077499546530L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long staffId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 应用名
     */
    private String applicationName;

}
