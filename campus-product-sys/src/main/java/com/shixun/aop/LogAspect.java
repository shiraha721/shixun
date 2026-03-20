package com.shixun.aop;

import com.shixun.context.LocalThreadHolder;
import com.shixun.mapper.OperationLogMapper;
import com.shixun.pojo.entity.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

//这是日志记录的核心切面类，通过 AOP 环绕通知机制，
// 拦截所有标记了 @Log 注解的业务接口，
// 在接口执行时自动创建并保存操作日志


@Aspect  //标记为切面类
@Component
public class LogAspect {

    @Resource
    private OperationLogMapper operationLogMapper;

    /**
     * 环绕通知，用于处理带有@Log注解的方法
     *
     * @param joinPoint 连接点
     * @param log       注解实例
     * @return 原方法执行的结果
     * @throws Throwable 异常
     */
    @Around("@annotation(log)")  // 拦截所有加了 @Log 注解的接口
    public Object handlePageableParams(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        OperationLog logEntity = createLogEntity(log);
        operationLogMapper.save(logEntity);
        return joinPoint.proceed();
    }

    /**
     * 创建操作日志实体
     *
     * @param log 操作日志注解
     * @return OperationLog
     */
    private OperationLog createLogEntity(Log log) {
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(LocalDateTime.now());
        operationLog.setDetail(log.detail());
        operationLog.setUserId(LocalThreadHolder.getUserId());
        return operationLog;
    }

}
