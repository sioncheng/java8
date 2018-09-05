package com.github.sioncheng.j.classloader;

import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

    public MyClassLoader(String classesPath) {
        this.classesPath = classesPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if ("myString".equals(name)) {
            System.out.println("return String as myString");
            return "String".getClass();
        } else if ("myClass".equals(name)) {
            String classPath = this.classesPath + "com/github/sioncheng/j/classloader/MyClass.class";
            try {
                FileInputStream fs = new FileInputStream(classPath);
                byte[] bytes = new byte[fs.available()];
                fs.read(bytes);

                return defineClass("com.github.sioncheng.j.classloader.MyClass", bytes, 0, bytes.length);
            } catch (Exception ex) {
                throw  new ClassNotFoundException(ex.getMessage());
            }
        } else {
            return null;
        }
    }

    private String classesPath;
}
