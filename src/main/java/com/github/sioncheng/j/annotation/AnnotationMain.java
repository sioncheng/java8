package com.github.sioncheng.j.annotation;

import java.lang.annotation.Annotation;

public class AnnotationMain {

    public static void main(String[] args) {
        ClassCombination c = new ClassCombination();

        Annotation[] annotations = c.getClass().getDeclaredAnnotations();
        for (Annotation annotation: annotations) {
            System.out.println(annotation.getClass());
        }

        Annotation[] annotations1 = c.getClass().getAnnotations();
        for (Annotation an :
                annotations1) {
            System.out.println(an.getClass());
        }

        System.out.println(c.getClass().getAnnotation(AnnotationA.class));
    }
}
