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
 * @date 2023/3/10 9:58
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("staff_auth_email")
public class StaffAuthEmailDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 4693155700165619110L;

    @TableId(type = IdType.INPUT)
    private Long staffId;

    private String email;

    private String password;
}
