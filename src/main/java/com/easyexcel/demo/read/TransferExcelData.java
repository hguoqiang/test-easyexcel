package com.easyexcel.demo.read;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-14 17:35
 **/
@Setter
@Getter
@ToString
public class TransferExcelData {

    /**
     * 序号
     */
    // @ExcelProperty(value = {"No\nลำดับ"})
    //private String serialNo;

    /**
     * 收款方NO
     */
    @ExcelProperty(value = {"Payee No\nNo ผู้รับเงิน"})
    private String payeeCustomerNo;

    /**
     * 收款方账户名
     */
    @ExcelProperty(value = {"Payee Account Name*\nชื่อบัญชีผู้รับเงิน*"})
    private String payeeAccountName;

    /**
     * 收款方账号
     */
    @ExcelProperty(value = {"Payee Account Number*\nหมายเลขบัญชีผู้รับเงิน*"})
    private String payeeAccountNo;


    /**
     * 收款方银行
     */
    @ExcelProperty(value = {"Payee Bank*\nธนาคารผู้รับเงิน*"})
    private String bankName;

    /**
     * 转账金额（泰铢）
     */
    @ExcelProperty(value = {"Transfer Amount(Baht)*\nจำนวนเงิน (บาท)*"})
    private BigDecimal amount;

    /**
     * 收款方手机号
     */
    @ExcelProperty(value = {"Payee Phone Number\nเบอร์มือถือผู้รับเงิน"})
    private String payeeAccountMobile;

    /**
     * 收款方邮箱
     */
    @ExcelProperty(value = {"Payee Email\nอีเมล์ผู้รับเงิน"})
    private String payeeAccountEmail;

    /**
     * 转账说明1
     */
    @ExcelProperty(value = {"Description 1\nรายละเอียด 1"})
    private String description1;
    /**
     * 转账说明2
     */
    @ExcelProperty(value = {"Description 2\nรายละเอียด 2"})
    private String description2;


}
