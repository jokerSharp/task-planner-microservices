package org.example.plannerutils.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log
public class LoggingAspect {

    @Around("execution(* org.example.*.controller..*(..)))")
    public Object profileControllerMethods(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        log.info("---Executing" + className + "." + methodName + "()---");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result =  pjp.proceed();
        stopWatch.stop();
        log.info("---Execution time of " + className + "." + methodName + "() is " + stopWatch.getTotalTimeMillis() + " ms---");
        return result;
    }
}
