package com.granda.mogolog;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author by guanda
 * @Date 2018/11/30 15:27
 */
public class ExceptionUtil {
    public static Map<String, Integer> findLineByPackage(String content, String packageName){
        Map<String, Integer> map = new LinkedHashMap<>();
        String[] lines = content.split("\n");
        for(String line : lines){
            if(line.split(" ")[1].startsWith(packageName)){
                line = line.split("\\(")[1].split("\\)")[0];
                if(line.contains(":")){
                    map.put(line.split("\\:")[0], Integer.valueOf(line.split("\\:")[1]));
                }
            }
        }
       return map;
    }

    public static void main(String[] args) {
        String content = "java.lang.RuntimeException: test\n" +
                "\tat com.example.demo.TestService.test(TestService.java:12)\n" +
                "\tat com.example.demo.TestController.getInt(TestController.java:20)\n" +
                "\tat com.example.demo.TestController$$FastClassBySpringCGLIB$$d8a6afe8.invoke(<generated>)\n" +
                "\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n" +
                "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:746)\n" +
                "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n" +
                "\tat org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)";
        Map<String, Integer> map = findLineByPackage(content, "com.example.demo");
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
