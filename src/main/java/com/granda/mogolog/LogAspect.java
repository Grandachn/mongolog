package com.granda.mogolog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

@Aspect
public class LogAspect {

    ThreadLocal<Long> runTime = new ThreadLocal<>();
    String className = "org.springframework.web.bind.annotation.GetMapping";


    public LogAspect(){
    }

    /**
     * 对指定注解，进行横切，创建一个横切的对象方法
     * 
     */
    @Pointcut("@annotation(com.granda.mogolog.LogToMongo)")
    public void annotationPoint(){};


    /**
     * 对横切方法，进行反射处理，对使用了注解方法“前”：不仅可以捕捉到注解内容，还有方法名等，
     * 此处的作用主要是：可以对使用注解使用的方法，进行方法特殊逻辑处理（可以具体到哪个方法使用了哪个注解内容进行特殊处理）
     * 
     */
    @Before("annotationPoint()")
    public void BeforeAnnotation(JoinPoint joinPoint){
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        Method method=signature.getMethod();
//        Action action=method.getAnnotation(Action.class);
        runTime.set(System.currentTimeMillis());
        System.out.println("注解的拦截方法名注解内容前：" + method.getName());
//        System.out.println(method.getParameterTypes()[0].getName());
//        System.out.println("注解的拦截方法名注解内容前：args：" + joinPoint.getArgs()[0]);
    }

    /**
     * 对横切方法，进行反射处理，对使用了注解方法“后”：不仅可以捕捉到注解内容，还有方法名等，
     * 此处的作用主要是：可以对使用注解使用的方法，进行方法特殊逻辑处理（可以具体到哪个方法使用了哪个注解内容进行特殊处理）
     * 
     */
    @After("annotationPoint()")
    public void AfterAnnotation(JoinPoint joinPoint){
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
//        GetMapping annotation = method.getAnnotation(GetMapping.class);
//        System.out.println("注解的拦截方法名注解内容后：" + method.getName());
//        System.out.println("Api_url: " + annotation.value()[0]);
        long costTime = System.currentTimeMillis() - runTime.get();
        System.out.println("costTime: " + costTime);
        System.out.println("return");

    }

    @AfterThrowing(pointcut = "annotationPoint()", throwing = "e")//切点在webpointCut()
    public void handleThrowing(JoinPoint joinPoint, Exception e) {//controller类抛出的异常在这边捕获
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        //开始打log
        System.out.println("异常:" + e.getMessage());
        System.out.println("异常所在类：" + className);
        System.out.println("异常所在方法：" + methodName);
        System.out.println("异常中的参数：");
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i].toString());
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        System.out.println("异常信息详情：" + sw.toString());
        System.out.println(methodName);
    }
}
