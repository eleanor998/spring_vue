package com.tbc.demo.catalog.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.tbc.demo.entity.User;
import com.tbc.demo.utils.TestFileUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EasyExcelTest {

    private static String filePath = "C:\\Users\\gekang\\Desktop\\标签导入模板.xls";


    public static void main(String[] args) {
        User user = new User();
        user.setUserName("张三");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.get(0).setUserName("李四");
        System.out.println(user);
        Map<String, User> collect = users.stream().collect(Collectors.toMap(User::getUserName, Function.identity()));
        collect.values().stream().findFirst().get().setUserName("母鸡");
        System.out.println(user);
    }

    public static ExcelWriterBuilder setTitle(File file) {
        if (file == null || file.exists() || !file.isFile()) {
            return null;
        }
        //合并列
        OnceAbsoluteMergeStrategy rowIndex1 = new OnceAbsoluteMergeStrategy(0, 0, 0, 10);
        OnceAbsoluteMergeStrategy rowIndex2 = new OnceAbsoluteMergeStrategy(1, 1, 0, 10);
        //设置颜色
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, headWriteCellStyle);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        ExcelWriterBuilder write = EasyExcel.write(file.getPath(), ImportTemplate.class);
        write.registerWriteHandler(rowIndex1);
        write.registerWriteHandler(rowIndex2);
        write.registerWriteHandler(horizontalCellStyleStrategy);
//        write.sheet("测试").doWrite(data());
        return null;
    }

}
