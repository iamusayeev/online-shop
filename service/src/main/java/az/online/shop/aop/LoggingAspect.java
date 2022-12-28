package az.online.shop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Around("az.online.shop.aop.CommonPointcuts.isServiceLayer()")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("before - invoked method: {} in class: {} args: {}", joinPoint.getSignature().getName(), joinPoint.getTarget(), joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.info("after returning - invoked method: {} in class: {} args: {} result: {}", joinPoint.getSignature().getName(),
                    joinPoint.getTarget(), joinPoint.getArgs(), result);
            return result;
        } catch (Throwable ex) {
            log.error("after throwing - invoked method: {} in class: {} with exception message: {}", joinPoint.getSignature(), joinPoint.getTarget(),
                    ex.getMessage());
            throw ex;
        }
    }
}