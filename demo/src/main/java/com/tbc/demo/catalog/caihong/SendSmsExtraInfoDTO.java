package com.tbc.demo.catalog.caihong;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SendSmsExtraInfoDTO implements Serializable {

    /**
     * 是否外部服务(外部服务会设置为true)
     */
    private Boolean channelService = false;

    /**
     * 发送人ID
     */
    private String sendUserId;

    /**
     * 发送人姓名
     */
    private String sendUserName;
}
