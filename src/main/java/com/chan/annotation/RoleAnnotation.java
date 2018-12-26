package com.chan.annotation;

import java.lang.annotation.*;

/**
 * @Target:声明将该注解标注在什么位置位置 METHOD:方法级
 * @Retention:注解的使用范围 RUNTIME:运行时
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAnnotation {

    Role[] value() default {};

    Role[] role() default {};
}
