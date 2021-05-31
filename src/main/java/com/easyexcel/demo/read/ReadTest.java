package com.easyexcel.demo.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.DefaultConverterLoader;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 读的常见写法
 * @author: huangguoqiang
 * @create: 2021-05-11 20:49
 **/
public class ReadTest {


    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        //String fileName = "D:\\test" + File.separator + "demo.xls";
        String fileName = "D:\\test" + File.separator + "null.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();


        // 写法2：
       /* fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }*/
    }


    /**
     * 指定列的下标或者列名
     *
     * <p>
     * 1. 创建excel对应的实体对象,并使用{@link ExcelProperty}注解. 参照{@link IndexOrNameData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link IndexOrNameDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void indexOrNameRead() {
        String fileName = "D:\\test"  + File.separator + "demo.xls";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }
    @Test
    public void TransferExcelDataRead() {
        String fileName = "D:\\test"  + File.separator + "批量转账模板-20210415.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, TransferExcelData.class, new TransferExcelDataReadListener()).sheet().doRead();
    }

    /**
     * 日期、数字或者自定义格式转换
     * <p>
     * 默认读的转换器{@link DefaultConverterLoader#loadDefaultReadConverter()}
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}.里面可以使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ConverterDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void converterRead() {
        String fileName = "D:\\test"  + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ConverterData.class, new ConverterDataListener())
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringStringConverter())
                // 读取sheet
                .sheet().doRead();
    }

    /**
     * 读取表头数据
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void headerRead() {
        String fileName = "D:\\test"  + File.separator + "demo.xls";
         fileName = "C:\\Users\\hguoq\\Desktop"  + File.separator + "Bulk Transfer Template.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoData.class, new DemoHeadDataListener()).sheet().doRead();
    }


    @Test
    public void headerRead11() {
        String fileName = "C:\\Users\\hguoq\\Desktop"  + File.separator + "Bulk Transfer Template.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, TransferExcelData.class, new TransferExcelDataReadListener()).sheet().doRead();
    }

    @Test
    public void add(){

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<TransferExcelData> allList = new ArrayList<>();
        TransferExcelData d1 = new TransferExcelData();
        TransferExcelData d2 = new TransferExcelData();
        TransferExcelData d3 = new TransferExcelData();
        d1.setAmount(new BigDecimal(12));
        d2.setAmount(new BigDecimal(12));
        d3.setAmount(new BigDecimal(12));
        allList.add(d1);
        allList.add(d1);
        allList.add(d1);

        BigDecimal reduce = allList.stream().map(TransferExcelData::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(reduce);
        System.out.println("201020235711498241_batch_tansfer_20210520211642.xlsx".length());
    }
}
