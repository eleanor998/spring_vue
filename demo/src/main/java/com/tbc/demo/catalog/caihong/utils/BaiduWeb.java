package com.tbc.demo.catalog.caihong.utils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("seller-open")
@RestController
public class BaiduWeb {


    @RequestMapping("order/detail")
    public String detail() {
        return "{\n" +
                "  \"data\": \"{\\\"backMoneyFen\\\":0,\\\"prescriptionId\\\":0,\\\"orderId\\\":\\\"1011686902067563565\\\",\\\"city\\\":\\\"\\\",\\\"deliverNo\\\":\\\"\\\",\\\"appointTimeRange\\\":\\\"2023-07-17 下午\\\",\\\"goods\\\":[{\\\"categoryLevel1\\\":\\\"疫苗服务\\\",\\\"amount\\\":1,\\\"userPayment\\\":1,\\\"goodsId\\\":\\\"1029695989490409472\\\",\\\"categoryLevel3\\\":\\\"带状疱疹疫苗\\\",\\\"categoryLevel2\\\":\\\"带状疱疹\\\",\\\"tpSkuId\\\":\\\"\\\",\\\"discount\\\":0,\\\"spec\\\":\\\"1\\\",\\\"titleName\\\":\\\"【到店付+分时预约】测试带状疱疹疫苗1\\\",\\\"headImgUrl\\\":\\\"https://drug-platform.cdn.bcebos.com/offline/drug/d1681980824051542992.png\\\",\\\"bdDiscount\\\":0,\\\"tpSkuCode\\\":\\\"\\\",\\\"licenseId\\\":\\\"1234456\\\",\\\"skuId\\\":1029695989490409472,\\\"priceFen\\\":1}],\\\"remark\\\":\\\"\\\",\\\"type\\\":2,\\\"appointDate\\\":\\\"2023-07-17\\\",\\\"appointUserName\\\":\\\"解丽萍\\\",\\\"appointType\\\":10,\\\"payType\\\":8,\\\"tpStoreCode\\\":\\\"\\\",\\\"preferentialPriceFen\\\":0,\\\"province\\\":\\\"\\\",\\\"marketPriceFen\\\":0,\\\"record\\\":[{\\\"operationType\\\":\\\"用户提交订单\\\",\\\"message\\\":\\\"用户提交订单，支付方式：到店支付,预约时间：2023-7-17 下午，接种人姓名：解*萍\\\",\\\"operator\\\":\\\"用户\\\",\\\"operationTime\\\":[2023,6,16,15,54,28]},{\\\"operationType\\\":\\\"交易关闭\\\",\\\"message\\\":\\\"商家超时未确认预约信息\\\",\\\"operator\\\":\\\"系统\\\",\\\"operationTime\\\":[2023,6,16,16,14,34]}],\\\"bdDiscount\\\":0,\\\"storeName\\\":\\\"疫苗1店\\\",\\\"receMobile\\\":\\\"15311806206\\\",\\\"appointMobile\\\":\\\"18595141887\\\",\\\"shopId\\\":280005685580873728,\\\"payTimeOut\\\":1686902967000,\\\"originalPriceFen\\\":1,\\\"payName\\\":\\\"到店支付\\\",\\\"auditAppointReason\\\":\\\"商家超时未确认预约信息,系统驳回\\\",\\\"deliverPriceFen\\\":0,\\\"vaccinatedPerson\\\":{\\\"birthday\\\":\\\"1968-12-28\\\",\\\"allergicHistory\\\":\\\"\\\",\\\"haveAllergicHistory\\\":1,\\\"appointTime\\\":\\\"2023-7-17 下午\\\",\\\"phone\\\":\\\"18595141887\\\",\\\"patientId\\\":\\\"332531683722081875\\\",\\\"idCard\\\":\\\"142702196812280046\\\",\\\"sex\\\":\\\"女\\\",\\\"name\\\":\\\"解丽萍\\\",\\\"cardType\\\":\\\"身份证\\\",\\\"queueNumber\\\":\\\"0\\\",\\\"appointAddress\\\":\\\"北京市海淀区鹏寰国际大厦\\\"},\\\"userPayment\\\":0,\\\"auditAppointStatus\\\":3,\\\"refundTime\\\":1686903307000,\\\"stores\\\":[{\\\"storeName\\\":\\\"疫苗1店\\\"}],\\\"storeId\\\":\\\"st163834360856328175\\\",\\\"message\\\":\\\"\\\",\\\"applyRefundTime\\\":1686903277000,\\\"companyId\\\":1000000241,\\\"receAddressDetail\\\":\\\"\\\",\\\"chufangFlag\\\":0,\\\"createTime\\\":1686902068000,\\\"paidAmountFen\\\":0,\\\"goodsPriceFen\\\":1,\\\"district\\\":\\\"\\\",\\\"deliverName\\\":\\\"\\\",\\\"newStoreId\\\":163834360856328175,\\\"status\\\":130,\\\"receName\\\":\\\"\\\"}\",\n" +
                "  \"logId\": 1687312360339803,\n" +
                "  \"status\": 0\n" +
                "}";
    }
}
