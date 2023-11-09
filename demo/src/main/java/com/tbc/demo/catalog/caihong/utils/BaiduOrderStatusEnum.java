package com.tbc.demo.catalog.caihong.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 对接百度健康订单状态
 */
public enum BaiduOrderStatusEnum {

	BOOKING_PENDING_SUBMIT(1, "预定待提交"),
	BOOKING_PENDING_CONFIRMATION(2, "预定待确认"),
	BOOKING_FAILED(4, "预定失败待同步"),
	BOOKING_SUCCESS_PENDING_SYNC(8, "预定成功待同步"),
	FAILED_SYNC(16, "状态同步失败"),
	SUCCESS_SYNC(1024, "状态同步成功"),
	CANCEL_ORDER(2048, "取消订单"),
	CANCEL_FAILED(4096, "取消订单失败"),
	CONFIRM_VACCINATION(8192, "确认接种");

	private String msg;
	private Integer code;
	private static Map<Integer, String> map = init();

	BaiduOrderStatusEnum(Integer code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public static String getMsg(Integer code) {
		return map.get(code);
	}

	public static String getCode(String msg) {
		return map.get(msg);
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	private static Map init() {
		Map<Integer, String> initMap = new HashMap<>();
		for (BaiduOrderStatusEnum value : values()) {
			initMap.put(value.code, value.getMsg());
		}
		return initMap;

	}


}
