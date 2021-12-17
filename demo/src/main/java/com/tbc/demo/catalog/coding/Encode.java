package com.tbc.demo.catalog.coding;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 注释
 *
 * @author gekangkang
 * @date 2019/7/31 16:37
 */
public class Encode {

    public static void main(String[] args) throws UnsupportedEncodingException {


        //汉字转换成ASCII码
        String str = "最近太他妈烦了谁能陪我说说话呀";
        char[] a = new char[str.length()];
        a = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            System.out.print((int)a[i]+" ");
            sb.append((int)a[i]+",");
        }

        //ASCII码转换成汉字
        String s = sb.toString() ;
        String[] s1 = s.split(",");
        int[] t = new int[s1.length];
        for (int i = 0; i < s1.length; i++) {
            t[i] = Integer.parseInt(s1[i]);
        }
        for (int i = 0; i < t.length; i++) {
            System.out.print((char)(t[i]));
        }

        //编码
        String encode = URLEncoder.encode(str, "UTF-8");

    }

}

