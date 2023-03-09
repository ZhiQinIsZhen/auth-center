package com.lyz.auth.api.admin.event.test;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.signature.qual.ClassGetName;
import org.springframework.context.ApplicationEvent;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 11:21
 */
@Getter
@Setter
public class TestHelloEvent extends ApplicationEvent {

    private final String msg;

    public TestHelloEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }
}
