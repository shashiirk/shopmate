package dev.shashiirk.shopmate.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging method calls.
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut to capture method calls within the application packages.
     */
    @Pointcut(
            "within(dev.shashiirk.shopmate.repository..*)" +
            " || within(dev.shashiirk.shopmate.service..*)" +
            " || within(dev.shashiirk.shopmate.controller..*)"
    )
    public void applicationPackagePointcut() {
    }

    /**
     * Get logger for the calling class.
     *
     * @param joinPoint The join point representing the intercepted method.
     * @return Logger instance for the calling class.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Advice to log before entering a method.
     *
     * @param joinPoint The join point representing the intercepted method.
     */
    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        Logger log = logger(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.debug("Enter: {}() with argument[s] = {}", methodName, Arrays.toString(args));
    }

    /**
     * Advice to log after returning from a method.
     *
     * @param joinPoint The join point representing the intercepted method.
     * @param result    The result returned by the intercepted method.
     */
    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        Logger log = logger(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        log.debug("Exit: {}() with result = {}", methodName, result);
    }

    /**
     * Advice to log after throwing an exception from a method.
     *
     * @param joinPoint The join point representing the intercepted method.
     * @param e         The exception thrown by the intercepted method.
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Logger log = logger(joinPoint);
        log.error(
                "Exception in {}() with cause = '{}' and exception = '{}'",
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL",
                e.getMessage(),
                e
        );
    }
}
