package com.tbc.demo.catalog.caihong.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.util.Map;

/**
 * 百对接百度健康http工具
 */

@Slf4j
public class BaiduUtils {

    private AppSigner appSigner;

    @Value("${baidu.jiankang.http.connectionTimeout:5000}")
    private Integer connectionTimeout;
    @Value("${baidu.jiankang.http.readTimeout:5000}")
    private Integer readTimeout;


    public BaiduUtils(AppSigner appSigner) {
        this.appSigner = appSigner;
    }

    /**
     * 向百度发健康发起请求，自动配置验签信息
     *
     * @param uri  请求uri
     * @param data 请求body数据
     * @param <T>
     * @return
     */
    public <T> String sendPostHttp(URI uri, T data) {
        String repBody = null;
        String reqBody = null;
        try {
            if (!(data instanceof String)) {
                reqBody = JSONObject.toJSONString(data);
            } else {
                reqBody = data.toString();
            }
            Map<String, String> map = appSigner.signGenerate(uri);
            HttpRequest post = HttpUtil.createPost(uri.toString());
            post.addHeaders(map);
            post.body(reqBody);
            repBody = post.execute().body();
            return JSON.toJSONString(JSONObject.parseObject(repBody));
        } catch (HttpException e) {
            log.error("{}", e);
        } finally {
            log.info("请求百度健康 url：{} 请求参数：{} 响应数据：{}", uri.toString(), JSON.toJSONString(JSONObject.parseObject(repBody), SerializerFeature.PrettyFormat), JSON.toJSONString(JSONObject.parseObject(repBody), SerializerFeature.PrettyFormat));
        }
        return "";
    }

    /**
     * 校验百度http请求发送的消息签名信息
     *
     * @param msg  请求体
     * @param sign 签名
     * @return
     */
    public boolean checkMsgSign(String msg, String sign) {
        if (StrUtil.hasBlank(msg, sign, sign)) {
            log.error("百度健康-非法请求");
            return false;
        }
        return sign.equals(appSigner.msgSign(msg));
    }

}
