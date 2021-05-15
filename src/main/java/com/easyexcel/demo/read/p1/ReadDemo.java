package com.easyexcel.demo.read.p1;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-14 16:45
 **/

public class ReadDemo {
    @Test
    public void ttest() {
        String msg;
        switch ("en") {
            case "th":
                msg = "หมายเลขแถว: %s , %s (รูปแบบไม่ถูกต้อง),ข้อมูล:%s";
                break;
            case "zh_CN":
                msg = "行号: %s , %s (格式不正确),数据为:%s";
                break;
            case "en":
                System.out.println();
            default:
                msg = "row number: %s , %s (incorrect format), data:%s";
                break;
        }

        System.out.println(msg);
    }

    @Test
    public void indexOrNameRead() {
        String fileName = "D:\\test" + File.separator + "demo.xlsx";

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("suffix: " + suffix);

        String hahha = String.format("第[%s]行，第[%s]列解析异常，数据为:%s", 1,
                23, "hahha");
        System.out.println(hahha);
        // 这里默认读取第一个sheet
        try {
            EasyExcel.read(fileName, THData.class, new ParentDataListener()).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("hhhhhhhhhhhhhhh");
    }

    private class ParentDataListener extends AnalysisEventListener<ParentData> {
        private final Logger LOGGER = LoggerFactory.getLogger(ParentDataListener.class);
        /**
         * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 5;
        List<ParentData> list = new ArrayList<ParentData>();
        List<String> parseErrorMsgList = new ArrayList<>();

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
            /*if (data.getBigDecimalData().compareTo(new BigDecimal("1.23")) == 0) {
                throw new RuntimeException("主动抛出");
            }*/
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
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            // LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
            // 如果是某一个单元格的转换异常 能获取到具体行号
            // 如果要获取头的信息 配合invokeHeadMap使用
            if (exception instanceof ExcelDataConvertException) {
                ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
      /*          LOGGER.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                        excelDataConvertException.getColumnIndex(),
                        excelDataConvertException.getCellData());*/

                ExcelContentProperty excelContentProperty = excelDataConvertException.getExcelContentProperty();
                String msg = String.format("第%s行，%s列解析异常，数据为:%s", excelDataConvertException.getRowIndex(),
                        excelContentProperty.getHead().getHeadNameList().get(0),
                        excelDataConvertException.getCellData());
                LOGGER.error(msg);

                parseErrorMsgList.add(msg);
                if (parseErrorMsgList.size() >= 3) {
                    String collect = parseErrorMsgList.stream().collect(Collectors.joining(System.getProperty("line.separator")));
                    System.out.println(collect);
                    throw new RuntimeException("超过了3条错误数据，不解析了");
                }

                System.out.println();

            } else {
                throw exception;
            }
        }
    }
}
