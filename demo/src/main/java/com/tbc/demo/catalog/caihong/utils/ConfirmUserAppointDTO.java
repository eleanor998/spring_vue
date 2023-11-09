package com.tbc.demo.catalog.caihong.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

/**
 * 百度接口确认预约请求实体
 */
@Data
@Builder
public class ConfirmUserAppointDTO {
	private String orderId;     // 订单编号
	private String storeId;     // 门店id
	private String shopId;      // 店铺id
	private Integer opType;     // 操作类型 1: 通过 2: 拒绝
	private String reason;      // 拒绝原因 (审核拒绝必填) - 日期无预约号源

	/**
	 * 对接百度健康需要外面嵌套一层data
	 *
	 * @return
	 */
	public String toInternalDataJsonString() {
		return "{\"data\": " + JSONObject.toJSONString(this) + "}";
	}
}
