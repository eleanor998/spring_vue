package com.tbc.demo.catalog.caihong.utils;

public class OrderSprint {
    public static void main(String[] args) {
        sprint(2048);
    }

    private static void sprint(Integer integer) {
        StringBuilder stringBuilder = new StringBuilder();
        for (BaiduOrderStatusEnum value : BaiduOrderStatusEnum.values()) {
            if ((integer & value.getCode()) != 0) {
                stringBuilder.append(value.getMsg());
                stringBuilder.append(";");
            }
        }
        System.out.println("订单状态为 " + stringBuilder);
    }
}
