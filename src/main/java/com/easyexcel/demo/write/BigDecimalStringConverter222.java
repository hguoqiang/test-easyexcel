package com.easyexcel.demo.write;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-31 10:01
 **/
public class BigDecimalStringConverter222 implements Converter<BigDecimal> {
    @Override
    public Class supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public BigDecimal convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        return cellData.getNumberValue();
    }

    @Override
    public CellData convertToExcelData(BigDecimal bigDecimal, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if(bigDecimal !=null) {
            return new CellData(bigDecimal.toPlainString());
        }
        return new CellData();
    }
}
