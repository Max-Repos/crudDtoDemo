package in.rajaram.userLogin.Aspect;

import in.rajaram.userLogin.Exception.CustomLoggingFailedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* in.rajaram.userLogin.Controller.UserDetailController.*(..))")
    public void loggingStarted() {
        // Empty
    }

    @Around("loggingStarted()")
    public Object loggingInfo(ProceedingJoinPoint joinPoint) {
        try {
            System.out.println("API started");
            Object res = joinPoint.proceed();
            System.out.println("API Ended");
            return res;
        } catch (Throwable e) {
            throw new CustomLoggingFailedException(e);
        }

    }
}
