package com.tbc.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.catalog.syn.Test;
import com.tbc.demo.entity.User;
import com.tbc.demo.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import java.io.IOException;
import java.util.regex.Pattern;

@RequestMapping("webDemo")
@Controller
@Slf4j
public class WebDemo {

    @RequestMapping("test")
    public void test() throws IOException {
        Test bean = SpringUtil.getBean(Test.class);
        bean.test(new User());
    }
}
