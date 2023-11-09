package com.tbc.demo.catalog.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotence {
    String key();

    String[] keys() default "";
}
