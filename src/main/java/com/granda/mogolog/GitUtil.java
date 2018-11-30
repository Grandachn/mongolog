package com.granda.mogolog;

import java.io.*;
import java.util.List;

/**
 * @Author by guanda
 * @Date 2018/11/30 13:52
 */
public class GitUtil {
    private static String PATH = "D:/test";

    public static void gitClone(){
        Runtime runtime = Runtime.getRuntime();
        try {
//            runtime.exec("cmd.exe /c cd D://");
            Process p = runtime.exec("git clone " + "git@github.com:Grandachn/testlog.git " ,new String[]{""}, new File("D:/"));
            InputStream inputStream = p.getInputStream();
            //
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gitPull(){
        Runtime runtime = Runtime.getRuntime();
        try {
//            runtime.exec("cmd.exe /c cd D://");
            Process p = runtime.exec("git pull ",new String[]{""}, new File("D:/testlog"));
            InputStream inputStream = p.getInputStream();
    //
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAuthor(String line){
        return line.split("\\(")[1].split(" ")[0];
    }

    public static void main(String[] args) throws IOException {
//        gitClone();
//        gitPull();

        List<File> files = FileUtil.searchFiles(new File("D:/testlog/"), "TestController.java");
        System.out.println("共找到:" + files.size() + "个文件");
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getParentFile().getAbsoluteFile());


            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("git blame TestController.java -L  1,10 ",new String[]{""}, file.getParentFile());
            InputStream inputStream = p.getInputStream();
            //
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
                System.out.println(getAuthor(line));
            }

        }
//        ProcessBuilder builder = new ProcessBuilder();
//        Runtime runtime = Runtime.getRuntime();
//        Process process = runtime.exec("cmd.exe /c dir d:\\");
//        InputStream inputStream = process.getInputStream();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
//        String line = null;
//        while((line = br.readLine()) != null) {
//            System.out.println(line);
//        }
    }
}
