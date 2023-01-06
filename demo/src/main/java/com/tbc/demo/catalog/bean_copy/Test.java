package com.tbc.demo.catalog.bean_copy;

import cn.hutool.core.bean.BeanUtil;

public class Test {
    public static void main(String[] args) {
        TestBean testBean = new TestBean();
        testBean.setOutUserID("123");
        testBean.setUserAccountID("23423");
        testBean.setUserID("654645");
        testBean.setType("76575");
        testBean.setRealName("98699");
        testBean.setAvatarUrl("2342");
        testBean.setSex(0);
        testBean.setPhone("345");
        testBean.setEmail("456457");
        testBean.setCardNumber("6546565");
        TestBean1 testBean1 = new TestBean1();
        BeanUtil.copyProperties(testBean, testBean1);
        System.out.println(testBean1);

    }
}
