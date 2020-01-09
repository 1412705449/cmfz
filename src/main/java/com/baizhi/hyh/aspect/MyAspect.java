package com.baizhi.hyh.aspect;

import com.baizhi.hyh.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
public class MyAspect {
    @Autowired
    HttpSession session;
    @Autowired
    LogService logService;

    @Around(value = "@annotation(com.baizhi.hyh.aspect.Log)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {
        /*时间*/
        Date date = new Date();
        /*人物*/
        //Admin admin = (Admin) session.getAttribute("admin");
        //String username = admin.getUsername();
        /*事件   获取方法名*/
        String name = proceedingJoinPoint.getSignature().getName();
        //获取自定义注解中的值
        //signature 中包存了该类该方法中的所有信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Log annotation = signature.getMethod().getAnnotation(Log.class);
        String value = annotation.value();
        //事情的执行结果
        Boolean flag = false;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            flag = true;
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            //com.baizhi.hyh.entity.Log log = new com.baizhi.hyh.entity.Log(UUID.randomUUID().toString().replace("-",""),username,value,name,date,flag);
            com.baizhi.hyh.entity.Log log = new com.baizhi.hyh.entity.Log(UUID.randomUUID().toString().replace("-", ""), "111", value, name, date, flag);
            //logService.insert(log);
        }
        return null;
    }
}
