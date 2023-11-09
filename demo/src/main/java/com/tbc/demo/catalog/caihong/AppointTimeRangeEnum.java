package com.tbc.demo.catalog.caihong;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 预约时间段：100000:上午 200000:下午
 *
 * @author cbd
 * @since 2023/6/26-9:43
 */
@Getter
@RequiredArgsConstructor
public enum AppointTimeRangeEnum {
	//
	AM("100000", "上午"),
	PM("200000", "下午");

	private final String code;

	private final String desc;

	public static boolean exists(String code) {
		for (AppointTimeRangeEnum value : AppointTimeRangeEnum.values()) {
			if (value.getCode().equals(code)){
				return true;
			}
		}
		return false;
	}
}
