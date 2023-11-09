package com.tbc.demo.catalog.caihong.temp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 服务单表 实体类
 *
 * @author cbd
 * @since 2023-07-03
 */
@Data
public class TradeServiceEntity {

	/**
	 * 下单人昵称
	 */
	@ApiModelProperty(value = "下单人昵称")
	private String contact;
	/**
	 * 下单人手机号
	 */
	@ApiModelProperty(value = "下单人手机号")
	private String mobile;
	/**
	 * 第三方订单ID
	 */
	@ApiModelProperty(value = "第三方订单ID")
	private String outId;
	/**
	 * 下单时间
	 */
	@ApiModelProperty(value = "下单时间")
	private Date tradeTime;
	/**
	 * 完成时间
	 */
	@ApiModelProperty(value = "完成时间")
	private Date finishTime;
	/**
	 * 服务单总金额
	 */
	@ApiModelProperty(value = "服务单总金额")
	private BigDecimal totalAmount;
	/**
	 * 实付金额
	 */
	@ApiModelProperty(value = "实付金额")
	private BigDecimal payAmount;
	/**
	 * 优惠金额
	 */
	@ApiModelProperty(value = "优惠金额")
	private BigDecimal preferentialAmount;
	/**
	 * 补贴金额
	 */
	@ApiModelProperty(value = "补贴金额")
	private BigDecimal subsidyAmount;
	/**
	 * 商品数量
	 */
	@ApiModelProperty(value = "商品数量")
	private Integer quantity;
	/**
	 * 意向城市
	 */
	@ApiModelProperty(value = "意向城市")
	private String preferredCity;
	/**
	 * 店铺名称
	 */
	@ApiModelProperty(value = "店铺名称")
	private String storeName;
	/**
	 * 规格ID
	 */
	@ApiModelProperty(value = "规格ID")
	private Long normsId;
	/**
	 * 品类ID
	 */
	@ApiModelProperty(value = "品类ID")
	private Long categoryId;
	/**
	 * 厂商ID
	 */
	@ApiModelProperty(value = "厂商ID")
	private Long factoryId;
	/**
	 * 医院ID
	 */
	@ApiModelProperty(value = "医院ID")
	private Long hospitalId;
	/**
	 * 针次
	 */
	@ApiModelProperty(value = "针次")
	private Integer needleCount;

	/**
	 * 平台ID
	 */
	private String platformId;

	/**
	 * 平台名称
	 */
	private String platformName;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 预约人ID
	 */
	private Long memberId;

	/**
	 * 是否存在退服务：0=否，1=是  到期时间
	 */
	private Integer refundService;

	/**
	 * 最晚安排时间
	 */
	private Date expireTime;

	/**
	 * 规格名称
	 */
	@ApiModelProperty(value = "规格名称")
	private String normsName;
	/**
	 * 品类名称
	 */
	@ApiModelProperty(value = "品类名称")
	private String categoryName;
	/**
	 * 厂商名称
	 */
	@ApiModelProperty(value = "厂商名称")
	private String factoryName;
	/**
	 * 医院名称
	 */
	@ApiModelProperty(value = "医院名称")
	private String hospitalName;

	private TradeServiceEntity sub;

}
