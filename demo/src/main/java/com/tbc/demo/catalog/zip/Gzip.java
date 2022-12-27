package com.tbc.demo.catalog.zip;

import cn.hutool.core.util.ZipUtil;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class Gzip {


    public static void main(String[] args) {
        byte[] gzip = ZipUtil.gzip(TestString.content.getBytes());
        System.out.println("压缩前" + ObjectSizeCalculator.getObjectSize(TestString.content));
        System.out.println("压缩后" + ObjectSizeCalculator.getObjectSize(gzip));
    }
}
