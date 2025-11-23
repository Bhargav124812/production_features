package aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//@Aspect
@Slf4j
@Component
public class LoggingAspectV2 {

    @AfterReturning(value="declaringPointCutAnnotation()",returning = "returnedObject")
    public void testingAfterReturning(JoinPoint joinPoint,Object returnedObject){
        log.info("The Returned Object is {}",returnedObject);
    }

    @Pointcut("execution(* aopApp.services.impl.ShipmentServiceImpl.*(..))")
    public void declaringPointCutAnnotation(){

    }

    @AfterThrowing("declaringPointCutAnnotation()")
    public void testIfException(JoinPoint joinPoint){
        log.info("The Exception is joinPoin {}",joinPoint.getSignature());
    }
}
