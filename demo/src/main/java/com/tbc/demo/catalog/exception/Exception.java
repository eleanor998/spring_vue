package com.tbc.demo.catalog.exception;

import com.tbc.bean.JsonResult;
import com.tbc.demo.catalog.asynchronization.model.User;
import com.tbc.exception.base.CustomEx;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常捕捉测试
 *
 * @author gekangkang
 * @date 2019/7/31 13:41
 */
@Data
@RequestMapping("exception")
@Slf4j
@RestController
public class Exception extends Throwable {

    public static void main(String[] args) {
        try {
            User user = null;
            user.getAge();
        } catch (java.lang.Exception e) {
            log.info("{}", e);
            log.info(e.getCause().toString());
        }
    }


    @RequestMapping("returnTest")
    public JsonResult returnTest() {
        try {
            exception();
        } catch (java.lang.Exception e) {
            return JsonResult.failure("ss");
        }
        return JsonResult.success();
    }

    /**
     * 测试抛大异常捕捉是否能够捕捉到子异常,匿名内部类测试 ,如果没有手动new异常的话,就算是throws追的的,也能够使用子异常捕捉到对应的实体,如果手动new 异常了的话就只能捕捉到手动创建的和更大的!
     *
     * @return
     * @throws java.lang.Exception
     */
    @Test
    public void catchTest() {
        try {
            exception();
        } catch (ArithmeticException e) {
            System.out.println("算术异常");
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            System.out.println("异常");
            e.printStackTrace();
        }
    }

    @Test
    public void consumerTest() throws java.lang.Exception {
        try {
            exception();
        } catch (java.lang.Exception e) {
            System.out.println(111);
        }
    }

    public void exception() throws java.lang.Exception {
        try {
            int a = 1 / 0;
            System.out.println(a);
        } catch (java.lang.Exception e) {
            // 异常+ 枚举规范返回参数!
            throw new CustomEx("算术异常");
        }
    }
}
