package com.easyexcel.demo.read.p1;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-14 16:45
 **/

public class ReadDemo {

    /**
     * 换行符
     */
    private static String LINE_SEPARATOR;

    /**
     * 全数字
     */
    private static final String INTEGER_REG = "^[\\d]*$";
    private static Pattern PATTERN_INTEGER_REG;

    /**
     * 数字 字母 下划线 中划线
     */
    private static Pattern PATTERN_EN_INTEGER_UNDERLINE_REG;
    private static final String EN_INTEGER_UNDERLINE_REG =  "^[0-9a-zA-Z_-]{1,}$";

    /**
     * 数字 字母
     */
    private static Pattern PATTERN_EN_INTEGER_REG;
    private static final String EN_INTEGER_REG = "^[0-9a-zA-Z]{1,}$";

    /**
     * 电子邮件
     */
    private static Pattern PATTERN_EMAIL_REG;
    private static final String EMAIL_REG = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
        PATTERN_INTEGER_REG = Pattern.compile(INTEGER_REG);
        PATTERN_EN_INTEGER_UNDERLINE_REG = Pattern.compile(EN_INTEGER_UNDERLINE_REG);
        PATTERN_EN_INTEGER_REG = Pattern.compile(EN_INTEGER_REG);
        PATTERN_EMAIL_REG = Pattern.compile(EMAIL_REG);
    }


    @Test
    public void test222() {

        System.out.println(PATTERN_EN_INTEGER_REG.matcher("88f8").matches());
        System.out.println(PATTERN_EMAIL_REG.matcher("921yg@qq.co").matches());
    }




    @Test
    public void test1() {

        if (new BigDecimal(0).compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("转账金额(必须大于0)");

        }


        String str1 = "12E+5";


        // Initializes two BigDecimal objects
        BigDecimal b_dec1 = new BigDecimal(str1);


        // By using toPlainString() method is
        // used to represent this BigDecimal b_dec1 as
        // a String without using the engineering notation
        String str_conv = b_dec1.toPlainString();
        String str_conv2 = b_dec1.toString();
        System.out.println("b_dec1.toPlainString(): " + str_conv);
        System.out.println("b_dec1.toString(): " + str_conv2);

        System.out.println("isAllNumber: " + isAllNumber("00012443"));


        System.out.println("--------------");
        boolean b = checkDecimalPoint(new BigDecimal("01.35"), 2);
        System.out.println(b);

    }

    /**
     * 判断字符串是否是全数字
     *
     * @param str
     * @return
     */
    private boolean isAllNumber(String str) {
        if (str == null) {
            return false;
        }
        return PATTERN_INTEGER_REG.matcher(str).matches();
    }

    /**
     * 校验数字小数点后是否超过指定的数位
     *
     * @param decimal       被校验数字
     * @param decimalPlaces 小数点位数
     * @return
     */
    private boolean checkDecimalPoint(BigDecimal decimal, int decimalPlaces) {
        if (decimal == null) {
            return false;
        }
        String decimalStr = decimal.toString();
        int index = decimalStr.lastIndexOf(".");
        if (index > 0) {
            if (decimalStr.substring(index + 1).length() > decimalPlaces) {
                System.out.println("小数点超过2位");
                return false;
            }
        }
        return true;
    }


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
           /* if (data.getFour() == null) {
                System.out.println("第四类数据是空的");
            }*/

            System.out.println(data.getBigDecimalData().toString() + " isAllNumber: " + isAllNumber(data.getBigDecimalData().toString()));


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


    @Test
    public void testPage() {
        int pageNum = 1;
        int totalPages;
        do {
            System.out.println("查询第：" + pageNum);
            totalPages = 6;

            pageNum++;

        } while (pageNum <= totalPages);
    }

    @Data
    private class TestData {

        private String payeeAccountName;
        private String payeeAccountNo;
        private String payeeAccountMobile;
        private String bankName;
        private BigDecimal amount;
        private String description;

    }

    @Test
    public void test2() {
        ArrayList checkErrorMsgList = new ArrayList<>();

        StringBuilder sp = null;
        for (int i = 0; i < 5; i++) {
            boolean saveFlag = false;
            TestData data = new TestData();

            sp = new StringBuilder("行号: ");
            sp.append(1);

            //行号：1  转账金额（必须输入），收款方账户名（必须输入），收款方账号（只可输入数字），收款方手机号（手机号位数不正确），转账说明（必须输入）
            //收款方账户名不能是空的
            if (org.apache.commons.lang3.StringUtils.isAllBlank(data.getPayeeAccountName())) {
                sp.append(", 收款方账户名(必须输入)");
                saveFlag = true;
            }
            //收款方账号只能是数字
            String payeeAccountNo = data.getPayeeAccountNo();
            if (StringUtils.isAllBlank(payeeAccountNo)) {
                saveFlag = true;
                sp.append(", 收款方账号(必须输入)");
            } else {
                if (!isAllNumber(payeeAccountNo)) {
                    saveFlag = true;
                    sp.append(", 收款方账号(只可输入数字)");
                }
            }

            //收款方手机号只能是数字 位数限制
            String payeeAccountMobile = data.getPayeeAccountMobile();
            if (StringUtils.isAllBlank(payeeAccountMobile)) {
                saveFlag = true;
                sp.append(" ,收款方手机号(必须输入)");
            } else {
                if (!isAllNumber(payeeAccountMobile)) {
                    saveFlag = true;
                    sp.append(" ,收款方手机号(只可输入数字)");
                }
            }
            //收款方银行名称不能是空
            if (StringUtils.isAllBlank(data.getBankName())) {
                saveFlag = true;
                sp.append(", 收款方银行(必须输入)");
            }

            //转账金额必须是数字 正数
            BigDecimal amount = data.getAmount();
            if (data.getAmount() == null) {
                sp.append(", 转账金额(必须输入)");
            } else {
                if (StringUtils.isAllBlank(amount.toString())) {
                    saveFlag = true;
                    sp.append(", 转账金额(必须输入)");
                } else {
                    //转账金额 可以是两位小数
                    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                        saveFlag = true;
                        sp.append(", 转账金额(必须大于0)");
                    }
                    if (!isAllNumber(amount.toString())) {
                        saveFlag = true;
                        sp.append(", 转账金额(只可输入数字)");
                    }
                    //如果有小数不能超过2位
                    if (!checkDecimalPoint(amount, 2)) {
                        saveFlag = true;
                        sp.append(", 转账金额(只可输入数字)");
                    }
                }
            }

            //转账说明必须输入
            if (StringUtils.isAllBlank(data.getDescription())) {
                saveFlag = true;
                sp.append(", 转账说明(必须输入)");
            }
            if (saveFlag) {

                checkErrorMsgList.add(sp.toString());
            }
        }

        System.out.println("-----------------------");
        checkErrorMsgList.forEach(System.out::println);
    }




}
