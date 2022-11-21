package com.tbc.demo.catalog.dingding;

import java.util.concurrent.ExecutionException;

public class SendMessage {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String s = "wuwei01(吴伟(0851049))";
        String s1 = s.split("\\(")[0];
        System.out.println(s1);
    }
}


