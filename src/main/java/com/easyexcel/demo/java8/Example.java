package com.easyexcel.demo.java8;

import java.util.regex.Pattern;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-27 13:18
 **/
public class Example {

    private  static final String INTEGER_REG = "^[\\d]*$";
    public static Pattern PATTERN_INTEGER_REG;
    static {
        PATTERN_INTEGER_REG = Pattern.compile(INTEGER_REG);
        System.out.println("static代码块");
    }


    /**
     * 数字 字母
     */
    public  Pattern PATTERN_EN_INTEGER_REG;
    private  final String EN_INTEGER_REG = "^[0-9a-zA-Z]{1,}$";


    {
        PATTERN_EN_INTEGER_REG = Pattern.compile(EN_INTEGER_REG);
        System.out.println("普通代码块");
    }


}
