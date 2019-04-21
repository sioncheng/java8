package com.github.sioncheng.j.classloader;

public class ClassLoaderMain {

    public static void main(String[] args) throws Exception {

        System.out.println(System.getProperty("java.system.class.loader"));

        System.out.println(ClassLoaderMain.class.getClassLoader().toString());
        System.out.println(ClassLoaderMain.class.getClassLoader().getParent().toString());
        //System.out.println(ClassLoaderMain.class.getClassLoader().getParent().getParent().toString());

        Class stringClazz = ClassLoaderMain.class.getClassLoader().loadClass("java.lang.String");

        MyClassLoader myClassLoader = new MyClassLoader(ClassLoaderMain.class.getResource("/").getPath());
        Class myStringClazz = myClassLoader.loadClass("myString");
        Class stringClazz2 = myClassLoader.loadClass("java.lang.String");

        Object str = stringClazz.newInstance();
        Object str2 = stringClazz2.newInstance();
        Object myStr = myStringClazz.newInstance();

        System.out.println(str.getClass().toString());
        System.out.println(str2.getClass().toString());
        System.out.println(myStr.getClass().toString());

        Class myClass = myClassLoader.loadClass("myClass");

        Object myc = myClass.newInstance();
        System.out.println(myc.toString());

        System.out.println(Thread.currentThread().getContextClassLoader().toString());
    }
}
