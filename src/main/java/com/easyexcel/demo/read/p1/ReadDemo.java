package com.easyexcel.demo.read.p1;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
        EasyExcel.read(fileName, THData.class, new ParentDataListener()).sheet().doRead();
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
            LOGGER.info("解析到一条数据:{}", data);

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
}
