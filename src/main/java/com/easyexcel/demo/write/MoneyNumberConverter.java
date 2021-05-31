package com.easyexcel.demo.write;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public class MoneyNumberConverter implements Converter<MonetaryAmount> {

    @Override
    public Class supportJavaTypeKey() {
        return MonetaryAmount.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public MonetaryAmount convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        BigDecimal value = cellData.getNumberValue();
        if(value != null) {
            return MoneyUtils.create(value);
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(MonetaryAmount value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (value != null) {
            return new CellData(value.getNumber().numberValue(BigDecimal.class));
        }
        return new CellData();
    }
}