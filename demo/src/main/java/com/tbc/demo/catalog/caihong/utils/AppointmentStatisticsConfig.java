package com.tbc.demo.catalog.caihong.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
public class AppointmentStatisticsConfig {


	/**
	 * 转发总开关
	 */

	private List<SkuList> skuList;

	@Data
	static class SkuList {
		private String name;

		private String code;
	}
}
