package com.easyexcel.demo.java8;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-27 13:20
 **/
public class App {

    public static void main(String[] args) {
        Example e1 = new Example();

        Example e2 = new Example();
        System.out.println(e1.PATTERN_INTEGER_REG == e2.PATTERN_INTEGER_REG);

        System.out.println(e1.PATTERN_EN_INTEGER_REG == e2.PATTERN_EN_INTEGER_REG);
    }
}
