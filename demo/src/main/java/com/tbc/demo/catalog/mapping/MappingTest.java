package com.tbc.demo.catalog.mapping;

import com.alibaba.fastjson.JSONObject;
import com.tbc.demo.catalog.asynchronization.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 注释
 *
 * @author gekangkang
 * @date 2019/8/11 19:37
 */
@RequestMapping("mapping")
@Controller
@Slf4j
public class MappingTest {

    /**
     *
     * 限定请求参数与请求方式
     *
     * params=”id”    :     请求参数中必须有id
     *
     * params=”!id”   :     请求参数中不能包含id
     *
     * params=”id=1”  :     请求参数中id的值必须为1
     *
     * params=”id!=1”  :    请求参数中id的值必须不能等于1
     *
     * params={“id”,”name”}  :   请求参数中必须包含id和name两个参数
     *
     * @GetMapping：相当于@RequestMapping(method=RequestMethod.GET)
     *
     * @PostMapping:相当于@RequestMapping(method=RequestMethod.POST)
     *
     * @PutMapping:相当于@RequestMapping(method=RequestMethod.PUT)
     *
     * @DeleteMapping:相当于@RequestMapping(method=RequestMethod.DELETE)
     *
     * @RequestParam("test") 接受请求中指定名称的参数,赋值到对应的参数,系统会默认添加,自动判断请求中的数据名称,与参数的名称
     * 进行映射, 如  http://localhost:8080/mapping/id?id=999&test=888 请求中会自动映射到对应的参数上!

     */
    @GetMapping(value="id",params="id")
    @ResponseBody
    public String test(String test,String id){
        return "限定请求方式,与请求参数映射!"+id+test;
    }


    @GetMapping("test1")
    @ResponseBody
    public String test1(User user){
        JSONObject json = (JSONObject) JSONObject.toJSON(user);
        return json.toString();
    }

}
