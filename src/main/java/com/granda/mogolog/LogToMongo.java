package com.granda.mogolog;

/**
 * @Author by guanda
 * @Date 2018/11/29 20:23
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogToMongo {

}
