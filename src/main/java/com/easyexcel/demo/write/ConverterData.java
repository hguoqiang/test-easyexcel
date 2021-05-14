package com.easyexcel.demo.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
public class ConverterData {
    /**
     * 我想所有的 字符串起前面加上"自定义："三个字
     */
    @ExcelProperty(value = "字符串标题", converter = CustomStringStringConverter.class)
    private String string;
    /**
     * 我想写到excel 用年月日的格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty("日期标题")
    private Date date;
    /**
     * 我想写到excel 用百分比表示
     */
    @NumberFormat("#.##%")
    @ExcelProperty(value = "数字标题")
    private Double doubleData;
}


      /*  INSERT INTO `goods` VALUES ('1',  'idx_prod11','prod11', '1000');
        INSERT INTO `goods` VALUES ('2',  'idx_prod12','prod12', '1000');
        INSERT INTO `goods` VALUES ('3',  'idx_prod13','prod13', '1000');
        INSERT INTO `goods` VALUES ('4',  'idx_prod14','prod14', '1000');
        INSERT INTO `goods` VALUES ('5',  'idx_prod15','prod15', '1000');
        INSERT INTO `goods` VALUES ('6',  'idx_prod16','prod16', '1000');
        INSERT INTO `goods` VALUES ('7',  'idx_prod17','prod17', '1000');
        INSERT INTO `goods` VALUES ('8',  'idx_prod18','prod18', '1000');
        INSERT INTO `goods` VALUES ('9',  'idx_prod19','prod19', '1000');*/
