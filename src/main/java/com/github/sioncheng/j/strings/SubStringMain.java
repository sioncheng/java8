package com.github.sioncheng.j.strings;

import java.io.UnsupportedEncodingException;

public class SubStringMain {

    public static void main(String[] args) throws Exception {
        String s = "您预订的02月10日，潮阳-深圳北，G6339已支付成功，我们会在02月11日16:02前持续为您抢票，抢票结果，我们会第一时间短信告知您，您也可以进入订单详情随时关注抢票进度。" ;

        System.out.println(maySubstring(s, 100));

    }

    private static String maySubstring(String content, int maxChars) throws UnsupportedEncodingException {


        StringBuilder sb = new StringBuilder();
        int currentLength = 0;
        for (char c : content.toCharArray()) {
            currentLength += String.valueOf(c).getBytes("UTF-8").length;
            if (currentLength <= maxChars - 3) {
                sb.append(c);
            } else {
                sb.append("...");
                break;
            }
        }

        return sb.toString();
    }
}
