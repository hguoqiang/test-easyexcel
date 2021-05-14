package com.easyexcel.demo.read.p1;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-14 16:45
 **/

public class ReadDemo {

    @Test
    public void indexOrNameRead() {
        String fileName = "D:\\test"  + File.separator + "demo.xlsx";
        // 这里默认读取第一个sheet
        try {
            EasyExcel.read(fileName, THData.class, new ParentDataListener()).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("hhhhhhhhhhhhhhh");
    }

    private class ParentDataListener extends AnalysisEventListener<ParentData> {
        private  final Logger LOGGER = LoggerFactory.getLogger(ParentDataListener.class);
        /**
         * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 5;
        List<ParentData> list = new ArrayList<ParentData>();

        @Override
        public void invoke(ParentData data, AnalysisContext context) {
           /* if(data instanceof THData){
                THData thData =(THData)data;
                BigDecimal bigDecimal = thData.getBigDecimalData().round(new MathContext(10, RoundingMode.HALF_UP));
                thData.setBigDecimalData(bigDecimal);
            }*/

          /*  try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            /*BigDecimal bigDecimal = data.getBigDecimalData().round(new MathContext(10, RoundingMode.HALF_UP));
            data.setBigDecimalData(bigDecimal);*/


            LOGGER.info("解析到一条数据:{}", data);
            if(data.getBigDecimalData().compareTo(new BigDecimal("1.23")) ==0){
                throw new RuntimeException("主动抛出");
            }
            list.add(data);
            if (list.size() >= BATCH_COUNT) {
                saveData();
                list.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            saveData();
            LOGGER.info("所有数据解析完成！");
        }

        /**
         * 加上存储数据库
         */
        private void saveData() {
            LOGGER.info("{}条数据，开始存储数据库！", list.size());
            LOGGER.info("存储数据库成功！");
        }

        /**
         * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
         *
         * @param exception
         * @param context
         * @throws Exception
         */
        @Override
        public void onException(Exception exception, AnalysisContext context) {
            LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
            // 如果是某一个单元格的转换异常 能获取到具体行号
            // 如果要获取头的信息 配合invokeHeadMap使用
            if (exception instanceof ExcelDataConvertException) {
                ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
                LOGGER.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                        excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
            }
        }
    }
}
