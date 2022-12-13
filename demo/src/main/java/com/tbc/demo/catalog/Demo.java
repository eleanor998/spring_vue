package com.tbc.demo.catalog;


import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Demo {


    public static void main(String[] args) {
        Integer a = 1234554321;
        System.out.println(isPalindrome(a));
    }


    public static boolean isPalindrome(int x) {
        //思考：这里大家可以思考一下，为什么末尾为 0 就可以直接返回 false
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int revertedNumber = 0;
        while (revertedNumber < x) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
            System.out.println(x);
        }
        return x == revertedNumber || x == revertedNumber / 10;
    }

}



