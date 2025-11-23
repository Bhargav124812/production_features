package aopApp.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ValidationAspect {

    @Pointcut("execution(* aopApp.services.impl.ShipmentServiceImpl.*(..))")
    public void declaringPointCutAnnotation(){

    }

    @Around("declaringPointCutAnnotation()")
    public Object valiadteOrderId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
       Object args[]=proceedingJoinPoint.getArgs();
       if((Long)args[0]> 0)
           return proceedingJoinPoint.proceed();
       return "Cannote Have Order With Negative ";
    }
}
