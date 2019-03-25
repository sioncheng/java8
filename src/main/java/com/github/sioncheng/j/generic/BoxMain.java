package com.github.sioncheng.j.generic;

public class BoxMain {

    public static void main(String[] args) {
        BoxImpl<Integer> bi = new BoxImpl<>();
        bi.set(100);
        BoxUtil.showBox(bi);

        BoxImpl<Number> bn = new BoxImpl<>();
        bn.set(100);
        BoxUtil.showBox(bn);

        BoxUtil.showBoxOfNumbers(bi);
        BoxUtil.showBoxOfNumbers(bn);
    }
}
