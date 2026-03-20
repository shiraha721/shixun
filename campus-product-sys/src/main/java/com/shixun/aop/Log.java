package com.shixun.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录注解，用来描述操作内容的
 */
@Target({ElementType.PARAMETER, ElementType.METHOD}) //@Target 指定 @Log 注解只能加在方法上，比如我在商品发布接口方法上加这个注解，就表示这个接口需要记录日志
@Retention(RetentionPolicy.RUNTIME)  //@Retention 设置为 RUNTIME，是为了让 AOP 切面在程序运行时能识别到这个注解，进而执行日志记录逻辑
public @interface Log {
    /**
     * 操作
     *
     * @return String
     */
    String detail() default "";   //detail 属性是用来描述操作内容的，比如 @Log (detail = "发布校园二手商品")，这个描述会被存到日志表的 detail 字段里，方便后期查看日志时知道用户做了什么
}
