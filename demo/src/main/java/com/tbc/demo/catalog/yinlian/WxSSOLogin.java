package com.tbc.demo.catalog.yinlian;

import com.tbc.demo.catalog.unionpayLogin.WxCommonSSO;
import com.tbc.demo.catalog.unionpayLogin.WxCommonTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("user")
@Slf4j
public class WxSSOLogin {

    private JdbcTemplate jdbcTemplate = WxCommonTest.jdbcTemplate();

    @RequestMapping("isBind")
    @ResponseBody
    public String isBind(String phone) {
        List<WxCommonSSO> query = jdbcTemplate.query("select * from t_ps_wx_unionpay_sso where phone =?", new BeanPropertyRowMapper<WxCommonSSO>(WxCommonSSO.class), phone);
        return CollectionUtils.isEmpty(query) ? "当前手机号没有绑定" : "当前手机号已经绑定!";
    }

    @RequestMapping("pdf")
    public void pdf(HttpServletResponse response) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\gekang\\Desktop\\常用\\云闪付小程登录测试.pdf"));
        response.setCharacterEncoding("utf-8");
        fileUpload(fileInputStream, response.getOutputStream());
    }

    @RequestMapping("getPDF")
    public void getPDF(HttpServletResponse response) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\gekang\\Desktop\\常用\\云闪付小程登录测试.pdf"));
        response.setCharacterEncoding("utf-8");
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName=" + "YunSanFu.pdf");
        fileUpload(fileInputStream, response.getOutputStream());
    }

    public static void fileUpload(InputStream is, OutputStream os) throws Exception {
        byte[] b = new byte[1024 * 1024 * 10];
        int length = 0;
        while (true) {
            length = is.read(b);
            if (length < 0)
                break;
            os.write(b, 0, length);
        }

        is.close();
        os.close();

    }

}
