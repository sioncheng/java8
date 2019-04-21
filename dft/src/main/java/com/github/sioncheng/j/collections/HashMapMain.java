package com.github.sioncheng.j.collections;

import java.util.HashMap;

public class HashMapMain {

    public static void main(String[] args) {
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("1", "a");
        System.out.println(stringHashMap.get("1"));
        String s1 = new String("1");
        System.out.println(s1 == "1");
        System.out.println(stringHashMap.get(s1));

        HashMap<XYObject, String> xyObjectStringHashMap = new HashMap<>();
        xyObjectStringHashMap.put(XYObject.Builder.build(1,2), "(1,2)");
        System.out.println(xyObjectStringHashMap.get(XYObject.Builder.build(1,2)));
    }
}
