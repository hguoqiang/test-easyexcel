package com.easyexcel.demo.read.p1;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-14 16:42
 **/
@Data
public class THData extends ParentData{

    @ExcelProperty("第三列标题")
    private Date bigDecimalData;


//    @ExcelProperty("第二列标题")
//    private Date date;


    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("第一列标题")
    private Integer string;

    @Override
    public String toString() {
        return "THData{" +
                "bigDecimalData=" + bigDecimalData +
                ", string='" + string + '\'' +
                '}';
    }
}
