package com.granda.mogolog;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;

/**
 * @Author by guanda
 * @Date 2018/11/29 15:07
 */
public class LogUtil {
    public static void logException(Exception e) throws IOException {
        Runtime runtime = Runtime.getRuntime();

        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
// get pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);
        GitUtil.gitClone();

//        try {
//            Process p = runtime.exec("jstack " + pid);
//            InputStream fis = p.getInputStream();
//            //用一个读输出流类去读
//            InputStreamReader isr=new InputStreamReader(fis);
//            //用缓冲器读行
//            BufferedReader br=new BufferedReader(isr);
//            String line=null;
//            //直到读完为止
//            while((line=br.readLine())!=null)
//            {
//                System.out.println(line);
//            }
//            System.out.println("----------------------------------------------");
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

//        try {
//            Process p = runtime.exec("jmap " + pid);
//            InputStream fis = p.getInputStream();
//            //用一个读输出流类去读
//            InputStreamReader isr=new InputStreamReader(fis);
//            //用缓冲器读行
//            BufferedReader br=new BufferedReader(isr);
//            String line=null;
//            //直到读完为止
//            while((line=br.readLine())!=null)
//            {
//                System.out.println(line);
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        System.out.println("LogUtil异常信息详情：" + sw.toString());
        GitUtil.gitPull();
        Map<String, Integer> exceptionLineMap = ExceptionUtil.findLineByPackage(sw.toString(), "com.example.demo");
        for(Map.Entry<String, Integer> entry : exceptionLineMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());

            List<File> files = FileUtil.searchFiles(new File("D:/testlog/"), entry.getKey());
            System.out.println("共找到:" + files.size() + "个文件");
            for (File file : files) {
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getParentFile().getAbsoluteFile());
                Process p = runtime.exec("git blame "+ entry.getKey() + " -L " + entry.getValue() + "," + entry.getValue(),new String[]{""}, file.getParentFile());
                InputStream inputStream = p.getInputStream();
                //
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                String line = null;
                while((line = br.readLine()) != null) {
                    System.out.println(line);
                    System.out.println(GitUtil.getAuthor(line));
                }
            }
        }




//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        System.out.println(stackTrace[0].getClassName() + "\t"  + stackTrace[0].getMethodName() + "\t" + stackTrace[0].getLineNumber());
//        for (StackTraceElement element : stackTrace) {
//            System.out.println(element.getClassName() + "\t"
//                    + element.getMethodName() + "\t" + element.getLineNumber());
//        }
//        StackTraceElement log = stackTrace[1];
//        String tag = null;
//        for (int i = 1; i < stackTrace.length; i++) {
//            StackTraceElement element = stackTrace[i];
//            if (!element.getClassName().equals(log.getClassName())) {
//                tag = element.getClassName() + "." + element.getMethodName();
//                break;
//            }
//        }
//        if (tag == null) {
//            tag = log.getClassName() + "." + log.getMethodName();
//
//        }
//        System.out.println(tag);
    }
}
