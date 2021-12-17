package com.tbc.demo.catalog.cookie;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * cookie设置
 *
 * @author gekangkang
 * @date 2019/8/5 16:30temp
 */
@RequestMapping("Cookid")
@Controller
@Slf4j
public class Cookid {

    @RequestMapping("test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("name", "郑毅");
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8080/Cookid/test2");
        return "hello world";
    }

    @RequestMapping("test2")
    public String test2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
           for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                for (Cookie requestCookie : request.getCookies()) {
                    if (requestCookie.getName().equals("name")) {
                        System.out.println(requestCookie);
                    }
                }
                response.sendRedirect("http://localhost:8080/Cookid/test3");
            }
        }

        return "999999999899999999999999999";
    }


    @RequestMapping("test3")
    public String test3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")) {
                System.out.println(cookie.getValue());
                cookie.setMaxAge(0);
                response.sendRedirect("http://localhost:8080/temp/test");
            }
        }

        return "999999999899999999999999999";
    }


    @Test
    public void Test() throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("unionId", "");
        jsonObject.put("openId", "");
        jsonObject.put("nickname", "");
        jsonObject.put("headImgUrl", "");
        System.out.println(jsonObject);
    }

}
