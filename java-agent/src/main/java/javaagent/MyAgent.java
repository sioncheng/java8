package javaagent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException {

        inst.addTransformer(new MyTransformer());


        inst.retransformClasses();
    }
}
