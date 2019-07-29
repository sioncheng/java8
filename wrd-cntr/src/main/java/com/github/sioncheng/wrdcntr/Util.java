package com.github.sioncheng.wrdcntr;

class Util {

    public static final String PRIDE_AND_PREJUDICE_TXT = "/Users/sion/Downloads/PrideAndPrejudice.txt" ;
    public static final String ONE_DAY_TXT = "/Users/sion/Downloads/OneDay.txt";
    public static final String SHORT_TXT = "/Users/sion/Downloads/short.txt";

    public static boolean isAlpha(byte b) {
        return isUpperCaseChar(b) || isLowerCaseChar(b);
    }

    public static boolean isUpperCaseChar(byte b) {
        return b >= 65 && b <= 90;
    }

    public static boolean isLowerCaseChar(byte b) {
        return b >= 97 && b <= 122;
    }
}
