package com.github.sioncheng.j.collections;

import java.util.ArrayList;
import java.util.List;

public class IntersectionInList {

    public static <T extends Comparable> List<T>  intersection  (List<T> la, List<T> lb) {
        if (la ==  null || lb == null) {
            return new ArrayList<>();
        }

        if (la.isEmpty() || lb.isEmpty()) {
            return new ArrayList<>();
        }

        T laMin = la.get(0);
        T laMax = la.get(la.size() - 1);

        T lbMin = lb.get(0);
        T lbMax = lb.get(lb.size() - 1);

        if (lbMin.compareTo(laMax) == 1 || laMin.compareTo(lbMax) == 1) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        int ia = 0;
        int ib = 0;
        for(;ia < la.size() && ib < lb.size();) {
            int cmp = la.get(ia).compareTo(lb.get(ib));
            if ( cmp == 0) {
                result.add(la.get(ia));
                ia += 1;
                ib += 1;
            } else if (cmp == -1) {
                ia += 1;
            } else if (cmp == 1) {
                ib += 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }
}
