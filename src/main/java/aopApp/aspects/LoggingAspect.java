package aopApp.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Before("execution(* aopApp.services.impl.ShipmentServiceImpl.orderPackage(..))")
    public void beforeOrderPacakge(JoinPoint joinPoint){
        log.info("Befor ORder the pacake"+joinPoint.getKind());
    }

    @Before("declaringPointCutAnnotation()")
    public void beforeAnnotation(){
        log.info("Befor the The ANnotation");
    }

    @After("within(aopApp..*)")
    public void afterClass(){
        log.info("This is after the Method");
    }

    @Pointcut("@annotation(aopApp.aspects.MyAnnotation)")
    public void declaringPointCutAnnotation(){

    }

}
