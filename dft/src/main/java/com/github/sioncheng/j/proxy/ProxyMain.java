package com.github.sioncheng.j.proxy;

import java.lang.reflect.*;

public class ProxyMain {

    public static void main(String[] args) throws Exception {

        InvocationHandler invocationHandler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equalsIgnoreCase("toString")) {
                    return toString();
                }

                System.out.println(method);
                System.out.println(args);
                System.out.println("========================");
                return null;
            }

            @Override
            public String toString() {
                return "InvocationHandlerAnonymousImpl";
            }
        };

        Class clazz = Proxy.getProxyClass(ProxyMain.class.getClassLoader(), Interface.class);

        Method[] methods = clazz.getMethods();
        for (Method m :
                methods) {
            System.out.println(m.getName());
        }

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c :
                constructors) {
            System.out.println(c.getName());
            Parameter[] parameters = c.getParameters();
            for (Parameter p :
                    parameters) {
                System.out.println(p.getName());
                System.out.println(p.getType());
            }
        }

        Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});

        Interface anInterfaceProxy = (Interface) constructor.newInstance(new Object[]{invocationHandler});

        anInterfaceProxy.call(100);

        System.out.println(anInterfaceProxy.toString());
    }
}
