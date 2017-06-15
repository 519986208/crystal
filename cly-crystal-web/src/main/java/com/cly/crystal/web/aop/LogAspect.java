package com.cly.crystal.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 定义切面，记录日志<br>
 * 拦截http请求与返回值
 */
@Slf4j
@Service
@Aspect
public class LogAspect {

    /**
     * 线程级别，放置环绕通知的请求
     */
    private ThreadLocal<StringBuilder> httpRequestHolder = new ThreadLocal<StringBuilder>();

    /**
     * 记录请求时间
     */
    private ThreadLocal<Long>          requestTimeHolder = new ThreadLocal<Long>();

    /**
     * 拦截方法
     */
    @Pointcut("execution(public * com.cly.crystal.web.controller..*.*(..)) )")
    private void pointCutMethod() {
    }

    /**
     * 声明后置通知<br>
     * 返回方法的返回值<br>
     * 记录到日志文件中
     */
    @AfterReturning(pointcut = "pointCutMethod()", returning = "returnValue")
    public void after(Object returnValue) {
        StringBuilder builder = httpRequestHolder.get();
        builder.append("方法返回值：");
        builder.append(JSON.toJSONString(returnValue));
        builder.append("\n");
        builder.append("请求时间：");
        builder.append(System.currentTimeMillis() - requestTimeHolder.get() + "毫秒");
        builder.append("\n");
        log.info(builder.toString());
        httpRequestHolder.remove();
        requestTimeHolder.remove();
    }

    /**
     * 声明环绕通知<br>
     * 拦截请求路径<br>
     * 拦截http请求方法<br>
     * 拦截类名<br>
     * 拦截方法名<br>
     * 拦截请求参数<br>
     * 拦截RequestBody
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        requestTimeHolder.set(System.currentTimeMillis());
        String requestPath = WebContext.getRequestPath();
        String method = WebContext.getRequest().getMethod();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("请求路径：" + requestPath).append("\n");
        builder.append("http请求方式：" + method).append("\n");
        builder.append("类名：" + targetName).append("\n");
        builder.append("方法名：" + methodName).append("\n");
        builder.append("参数：");
        int length = arguments.length;
        for (int i = 0; i < length; i++) {
            Object obj = arguments[i];
            if (obj == null)
                continue;
            String value = JSON.toJSONString(obj);
            if (i == length - 1) {
                builder.append(value);
            } else {
                builder.append(value).append(" ");
            }
        }
        builder.append("\n");
        httpRequestHolder.set(builder);
        return joinPoint.proceed();
    }

}
