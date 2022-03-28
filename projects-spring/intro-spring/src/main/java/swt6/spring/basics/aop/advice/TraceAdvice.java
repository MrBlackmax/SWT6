package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class TraceAdvice {

    @DeclareParents(value = "swt6.spring.basics.aop.logic.*")
    private boolean isTracingEnabledInProxy(JoinPoint jp) {
        return ((TraceOptions) jp).isTracingEnabled();
    }

    @Pointcut("execution (public * swt6.spring.basics.aop.logic.WorkLogServiceImpl..*find*(..))")
    public void findMethods() {
    }

    @Before("findMethods()")
    public void traceBefore(JoinPoint jp) {
        if (isTracingEnabledInProxy(jp)) {
            String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
            System.out.println("--> " + methodName);

        }
    }

    @AfterReturning("findMethods()")
    public void traceAfter(JoinPoint jp) {
        if (isTracingEnabledInProxy(jp)) {
            String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
            System.out.println("<--" + methodName);
        }
    }


    public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();

        if (isTracingEnabledInProxy(pjp)) {
            System.out.println("==> " + methodName);
        }

        Object retVal = pjp.proceed();

        if (isTracingEnabledInProxy(pjp)) {
            System.out.println("<== " + methodName);
        }

        return retVal;
    }

    public void traceException(JoinPoint jp, Throwable exception) {
        if (isTracingEnabledInProxy(jp)) {
            String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
            System.out.println("err> " + methodName);
            System.out.println("exception: " + exception.getMessage());
        }
    }
}
