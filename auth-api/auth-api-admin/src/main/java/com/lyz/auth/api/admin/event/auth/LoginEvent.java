package com.lyz.auth.api.admin.event.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:28
 */
@Getter
@Setter
public class LoginEvent extends ApplicationEvent {

    private final Long staffId;

    private final String staffName;

    private final boolean login;

    public LoginEvent(Object source, Long staffId, String staffName, boolean login) {
        super(source);
        this.staffId = staffId;
        this.staffName = staffName;
        this.login = login;
    }
}
