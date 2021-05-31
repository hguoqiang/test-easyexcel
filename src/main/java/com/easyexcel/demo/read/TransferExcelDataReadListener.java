package com.easyexcel.demo.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-17 15:51
 **/

public class TransferExcelDataReadListener extends AnalysisEventListener<TransferExcelData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferExcelDataReadListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<TransferExcelData> list = new ArrayList<TransferExcelData>();

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        LOGGER.info("解析头结束");

    }

    @Override
    public void invoke(TransferExcelData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", data);

       /* if(data.getSerialNo() ==null ||"".equals(data.getSerialNo())){
            System.out.println("不解析");
            return;
        }*/
        String payeeCustomerNo = data.getPayeeCustomerNo();
        String payeeAccountName = data.getPayeeAccountName();
        String payeeAccountNo = data.getPayeeAccountNo();
        String bankName = data.getBankName();
        BigDecimal amount = data.getAmount();
        String payeeAccountMobile = data.getPayeeAccountMobile();
        String payeeAccountEmail = data.getPayeeAccountEmail();
        String description1 = data.getDescription1();
        String description2 = data.getDescription2();

        if (StringUtils.isBlank(payeeCustomerNo)
                && StringUtils.isBlank(payeeAccountName)
                && StringUtils.isBlank(payeeAccountNo)
                && StringUtils.isBlank(bankName)
                && amount == null
                && StringUtils.isBlank(payeeAccountMobile)
                && StringUtils.isBlank(payeeAccountEmail)
                && StringUtils.isBlank(description1)
                && StringUtils.isBlank(description2)) {
            LOGGER.info("此行数据是空的,不再继续 ");
            return;
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

}
