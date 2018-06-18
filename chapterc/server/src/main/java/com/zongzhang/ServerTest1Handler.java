package com.zongzhang;

/**
 * @author zongzhang
 * @since 1.0
 * <p>
 * create on 2018/6/14
 */
public class ServerTest1Handler {
    public static void main(String[] args) {
        String s = "A:A B：：B A:：B";
        System.out.println(s.replaceAll("[:：]", "").toUpperCase());
    }
}
