package com.tbc.demo.catalog.caihong;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class PersonalizedSmsDTO extends SendSmsExtraInfoDTO {

    /**
     * msgId
     */
    private String msgId;

    /**
     * 签名ID
     */
    private String signId;

    /**
     * 模版编号
     */
    private String templateId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 对应字段
     * key = 字段名称，value = 字段值
     * <p>
     * 短信模版内容例子：你好${name},当前时间为${date};
     * {"name" = "张三", "date" = "2023-08-10"}
     */
    private Map<String, String> correspondingFieldMap;
}
