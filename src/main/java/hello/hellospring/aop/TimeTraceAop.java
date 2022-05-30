package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
AOP(Aspect-oriented Programming)
관점 지향 프로그래밍
흩어진 Aspect를 모듈화 할 수 있는 프로그래밍 기법
핵심적인 관점 + 부과적인 관점으로 나누어 본다

AOP 적용 전 의존관계
memberController -> memberService

AOP 적용 후 의존 관계
memberController -> 프록시 memverService -joinPoint.proceed()> 실제 memberService
 */
@Component
@Aspect
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMS = finish - start;
            System.out.println("END: " +joinPoint.toString() + " " + timeMS + "ms");
        }
    }
}
