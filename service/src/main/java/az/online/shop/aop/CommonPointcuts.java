package az.online.shop.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommonPointcuts {

    @Pointcut("within(az.online.shop.service.*Service)")
    public void isServiceLayer() {
    }
}