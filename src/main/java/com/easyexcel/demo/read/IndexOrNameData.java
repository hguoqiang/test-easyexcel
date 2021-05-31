package com.easyexcel.demo.read;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Data

public class IndexOrNameData {
    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     */
    @ExcelProperty(index = 2)
    //@ExcelProperty("第三标题")
    private Double doubleData;


//    @ExcelProperty("第二列标题")
//    private Date date;

//    private String  haha;
//    private String  sssssssss;

    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("第一列标题")
    private String string;

    @Override
    public String toString() {
        return "IndexOrNameData{" +
                "doubleData=" + doubleData +
                ", string='" + string + '\'' +
                '}';
    }
}