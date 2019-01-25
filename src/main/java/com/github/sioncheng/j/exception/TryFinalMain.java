package com.github.sioncheng.j.exception;

import static java.lang.System.err;

public class TryFinalMain {

    public static void main(String[] args) {
        try {
            f();
        } catch (Exception ex) {
            err.println("Exception encountered: " + ex.toString());
            final Throwable[] suppressedExceptions = ex.getSuppressed();
            final int numSuppressed = suppressedExceptions.length;
            if (numSuppressed > 0) {
                err.println("tThere are " + numSuppressed + " suppressed exceptions:");
                for (final Throwable exception : suppressedExceptions)
                {
                    err.println("tt" + exception.toString());
                }
            }
        }
    }

    private static void f() {
        DirtyResource dirtyResource = new DirtyResource();
        try {
            dirtyResource.access();
        } finally {
            dirtyResource.close();
        }
    }
}
