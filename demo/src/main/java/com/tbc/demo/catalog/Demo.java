package com.tbc.demo.catalog;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Demo {
    public static void main(String[] args) throws NoSuchMethodException, IOException {
        List<String> list = new ArrayList<>();
    }

    /**
     * 如果有分组只查询分组内的数据，如果没有查询正则对应的数据
     *
     * @param regex
     * @param str
     * @return
     */
    private static String findRegexGroupStr(String regex, String str) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        StringBuilder stringBuilder = new StringBuilder();
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                stringBuilder.append(matcher.group(i));
            }
            if (StringUtils.isEmpty(stringBuilder)) {
                stringBuilder.append(matcher.group(0));
            }
        }
        return stringBuilder.toString();
    }
}



