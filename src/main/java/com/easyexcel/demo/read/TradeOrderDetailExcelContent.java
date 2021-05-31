package com.easyexcel.demo.read;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-31 09:37
 **/
public class TradeOrderDetailExcelContent {

    @ExcelProperty("付款账户")
    private String accountType;

    @ExcelProperty("订单金额")
    private BigDecimal amount;
}
