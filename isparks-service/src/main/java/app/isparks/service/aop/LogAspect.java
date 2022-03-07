package app.isparks.service.aop;

import app.isparks.core.service.ILogService;
import app.isparks.service.async.event.LogEvent;
import app.isparks.service.impl.LogServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @author： chenghd
 * @date： 2021/3/10
 */
@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ILogService logService;

    private ApplicationEventPublisher publisher;

    public LogAspect(LogServiceImpl logService,ApplicationEventPublisher publisher){
        this.logService = logService;
        this.publisher = publisher;
    }

    /**
     * 切入点
     *
     * 添加注解 app.isparks.core.anotation.Log 的方法做日志记录
     *
     */
    @Pointcut("@annotation(app.isparks.core.anotation.Log)")
    public void logPointCut(){

    }

    /**
     * 返回结果后记录日志
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterRetuning(JoinPoint joinPoint){
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取注解
        app.isparks.core.anotation.Log logAno = method.getAnnotation(app.isparks.core.anotation.Log.class);
        if(logAno == null){
            return;
        }
        publisher.publishEvent(new LogEvent(this,logAno.description(),logAno.types()));
    }

}
