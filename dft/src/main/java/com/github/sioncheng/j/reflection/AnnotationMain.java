package com.github.sioncheng.j.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationMain {

    public static void main(String[] args) throws Exception {
        MyClass myClass = new MyClass();
        myClass.execute();

        Class clazz = myClass.getClass();
        Method method = clazz.getMethod("execute", null);
        method.invoke(myClass);

        if (method.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation myA = method.getAnnotation(MyAnnotation.class);
            for (String val : myA.value()) {
                System.out.println(val);
            }
        }

        Package packagge = MyClass.class.getPackage();
        System.out.println(packagge.toString());
        System.out.println(packagge.getImplementationVersion());
        System.out.println(String.class.getPackage().getImplementationVersion());
    }
}
