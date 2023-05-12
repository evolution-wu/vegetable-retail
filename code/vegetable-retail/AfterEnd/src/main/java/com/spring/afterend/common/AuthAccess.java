package com.spring.afterend.common;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//解放权限，比如放送前端获取的所有的data数据
public @interface AuthAccess {
}
