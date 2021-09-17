package com.tbc.demo.catalog.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
public class StringTest {
    @Test
    public void test() {
        StringBuffer stringBuffer = new StringBuffer();
        String format = String.format("这个是测试%s", stringBuffer);
        System.out.println(format);
    }
}

