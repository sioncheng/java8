package com.github.sioncheng.j.exception;

import static java.lang.System.err;

public class TryWithResourceMain {

    public static void main(String[] args) {
        try {
            f();
        } catch (Exception ex) {
            err.println("Exception encountered: " + ex.toString());
            final Throwable[] suppressedExceptions = ex.getSuppressed();
            final int numSuppressed = suppressedExceptions.length;
            if (numSuppressed > 0) {
                err.println("\tThere are " + numSuppressed + " suppressed exceptions:");
                for (final Throwable exception : suppressedExceptions)
                {
                    err.println("\t\t" + exception.toString());
                }
            }
        }
    }

    private static void f() throws Exception{
        try (DirtyResource dirtyResource = new DirtyResource()){
            dirtyResource.access();
        }
    }
}
