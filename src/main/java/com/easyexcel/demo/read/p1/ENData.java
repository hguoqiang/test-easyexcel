package com.easyexcel.demo.read.p1;

import lombok.Data;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-14 16:43
 **/
@Data
public class ENData extends ParentData{


    /*@ExcelProperty("第二列标题")
    private Date date;



    *//**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     *//*
    @ExcelProperty("第一列标题")
    private String string;

    @Override
    public String toString() {
        return "ENData{" +
                "date=" + date +
                ", string='" + string + '\'' +
                '}';
    }*/
}
