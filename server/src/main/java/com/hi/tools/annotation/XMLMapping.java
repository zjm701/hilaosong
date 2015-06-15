package com.hi.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * XML映射
 * @author hexiang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE })
public @interface XMLMapping
{
    /**
     * xml节点对于映射名称
     * @return
     */
    String value();
}
