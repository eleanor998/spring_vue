//package com.tbc.demo.catalog.caihong;
//
//import com.jfinal.log.Log;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.internal.tmc.MessageHandler;
//import com.taobao.api.internal.tmc.TmcClient;
//import com.taobao.api.internal.toplink.LinkException;
//import com.taobao.api.request.AlibabaAlihealthVaccineTradeOrderChannelGetRequest;
//import com.taobao.api.request.AlibabaAlihealthVaccineTradeSubscribeDetailGetRequest;
//import com.taobao.api.request.AlibabaAlihealthVaccineTradeSubscribeDetailSaveRequest;
//import com.taobao.api.request.TradeFullinfoGetRequest;
//import com.taobao.api.response.AlibabaAlihealthVaccineTradeOrderChannelGetResponse;
//import com.taobao.api.response.AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse;
//import com.taobao.api.response.AlibabaAlihealthVaccineTradeSubscribeDetailSaveResponse;
//import com.taobao.api.response.TradeFullinfoGetResponse;
//
//public class TaobaoKit {
//    private static final Log log = Log.getLog(TaobaoKit.class);
//
//    private static final TaobaoClient client = new DefaultTaobaoClient(
//            TaobaoAccountConfig.getInstance().getUrlHttp(),
//            TaobaoAccountConfig.getInstance().getAppKey(),
//            TaobaoAccountConfig.getInstance().getSecret());
//
//    public enum AesKeyEnum {
//
//        AESKEY("nuqTf_+ghL!AuxJb"),
//        ;
//
//        public String aesKey;
//
//        AesKeyEnum(String aesKey) {
//            this.aesKey = aesKey;
//        }
//    }
//
//    /**
//     * 商家id
//     */
//    public enum SellerUserIdEnum {
//
//        TIANMAO(2206467706948L),
//        CAIHONG_YILU(2209690426651L),
//        YUMIAO_YILU(2214106990271L),
//        ;
//
//        public Long userId;
//
//        SellerUserIdEnum(Long userId) {
//            this.userId = userId;
//        }
//    }
//
//    /**
//     * 淘宝TMC订阅
//     */
//    public enum TaobaoTmcTopicEnum {
//
//        TRADE_BUYER_PAY("taobao_trade_TradeBuyerPay", "买家付完款，或万人团买家付完尾款"),
//        TRADE_SUCCESS("taobao_trade_TradeSuccess", "交易成功消息"),
//        REFUND_SUCCESS("taobao_refund_RefundSuccess", "退款成功消息"),
//        TRADE_VACCINE_SUBSCRIBE_CHANGE("alibaba_alihealth_TradeVaccineSubscribeChange", "预约变动消息"),
//        ;
//
//        public String name;
//        public String desc;
//
//        TaobaoTmcTopicEnum(String name, String desc) {
//            this.name = name;
//            this.desc = desc;
//        }
//    }
//
//    /**
//     * 订单详情
//     *
//     * @param  userId 商家id tid 订单ID
//     * @return
//     * @throws ApiException
//     */
//    public static TradeFullinfoGetResponse tradeFullinfoGet(Long userId,Long tid) {
//        //找到对应类，比如taobao.trade.fullinfo.get接口对应的请求类为TradeFullinfoGetRequest
//        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
//        //设置业务参数
//        req.setFields("tid,created,pay_time,buyer_nick,receiver_name,receiver_state,receiver_address,receiver_mobile,receiver_phone,type,status,payment,orders,promotion_details");
//        req.setTid(tid);
//        TradeFullinfoGetResponse rsp = null;
//        try {
//            if (userId.equals(SellerUserIdEnum.CAIHONG_YILU.userId)){
//                rsp = client.execute(req, TaobaoAccountConfig.getInstance().getCaihongSessionKey());
//            }else if (userId.equals(SellerUserIdEnum.YUMIAO_YILU.userId)){
//                rsp = client.execute(req, TaobaoAccountConfig.getInstance().getYumiaoSessionKey());
//            }else {
//                rsp = client.execute(req, TaobaoAccountConfig.getInstance().getSessionKey());
//            }
//
//
//            log.info(String.format("taobao-订单详情返回参数tid = %d response = %s", tid, rsp.getBody()));
//        } catch (ApiException e) {
//            e.printStackTrace();
//            log.error("taobao-订单详情查询错误", e);
//        }
//        return rsp;
//    }
//
//    /**
//     * 医鹿订单来源
//     *
//     * @param  userId 商家id bizOrderId 业务订单号  userId 卖家ID
//     * @return
//     * @throws ApiException
//     */
//    public static AlibabaAlihealthVaccineTradeOrderChannelGetResponse tradeOrderChannelGet(Long userId, Long bizOrderId) {
//        //找到对应类，比如taobao.trade.fullinfo.get接口对应的请求类为TradeFullinfoGetRequest
//        AlibabaAlihealthVaccineTradeOrderChannelGetRequest req = new AlibabaAlihealthVaccineTradeOrderChannelGetRequest();
//        AlibabaAlihealthVaccineTradeOrderChannelGetRequest.TradeVaccineOrderQueryTopRequest obj1 = new AlibabaAlihealthVaccineTradeOrderChannelGetRequest.TradeVaccineOrderQueryTopRequest();
//        obj1.setBizOrderId(bizOrderId);
//        obj1.setMerchantId(String.valueOf(userId));
//        req.setTradeVaccineOrderQueryTopRequest(obj1);
//        AlibabaAlihealthVaccineTradeOrderChannelGetResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//            log.info(String.format("医鹿-订单来源返回参数tid = %d response = %s", bizOrderId, rsp));
//        } catch (ApiException e) {
//            e.printStackTrace();
//            log.error("医鹿-订单来源查询错误", e);
//        }
//        return rsp;
//    }
//
//    /**
//     * 医鹿预约单信息
//     *
//     * @param userId 商家id bizOrderId 业务订单号  userId 卖家ID subscribeId 预约记录主键
//     * @return
//     * @throws ApiException
//     */
//    public static AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse tradeSubscribeDetailGet(Long userId, Long bizOrderId, Long subscribeId) {
//        //找到对应类，比如taobao.trade.fullinfo.get接口对应的请求类为TradeFullinfoGetRequest
//        AlibabaAlihealthVaccineTradeSubscribeDetailGetRequest req = new AlibabaAlihealthVaccineTradeSubscribeDetailGetRequest();
//        AlibabaAlihealthVaccineTradeSubscribeDetailGetRequest.TradeSubscribeDetailQueryTopRequest obj1 = new AlibabaAlihealthVaccineTradeSubscribeDetailGetRequest.TradeSubscribeDetailQueryTopRequest();
//        //设置业务参数
//        obj1.setBizOrderId(bizOrderId);
//        obj1.setMerchantId(String.valueOf(userId));
////        obj1.setSubscribeId(subscribeId);
//        req.setTradeSubscribeDetailQueryTopRequest(obj1);
//
//        AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//            log.info(String.format("医鹿-预约单详情返回参数tid = %d response = %s", bizOrderId, rsp));
//        } catch (ApiException e) {
//            e.printStackTrace();
//            log.error("医鹿-预约单详情查询错误", e);
//        }
//        return rsp;
//    }
//
//    /**
//     * 医鹿预约回流接口
//     *
////     * @param param 参数
//     * @return
//     * @throws ApiException
//     */
//    public static AlibabaAlihealthVaccineTradeSubscribeDetailSaveResponse tradeSubscribeDetailSave(TradeSubscribeDetailSaveParam param) {
////        log.info("医鹿预约数据回流参数：" + JsonKit.toJson(param));
//
//        //找到对应类，比如taobao.trade.fullinfo.get接口对应的请求类为TradeFullinfoGetRequest
//        AlibabaAlihealthVaccineTradeSubscribeDetailSaveRequest req = new AlibabaAlihealthVaccineTradeSubscribeDetailSaveRequest();
//        AlibabaAlihealthVaccineTradeSubscribeDetailSaveRequest.TradeSubscribeDetailExecuteTopRequest obj1 = new AlibabaAlihealthVaccineTradeSubscribeDetailSaveRequest.TradeSubscribeDetailExecuteTopRequest();
//
//        //设置业务参数
//        obj1.setIsvSubscribeId(param.getIsvSubscribeId());
////        obj1.setInjectionNum(param.getInjectionNum());
//        obj1.setSubscribeTime(param.getSubscribeTime());
//        obj1.setBizOrderId(param.getBizOrderId());
//        obj1.setMerchantId(param.getMerchantId());
////        obj1.setShopName(param.getShopName());
////        obj1.setCancelReason(param.getCancelReason());
////        obj1.setSocialCreditCode(param.getSocialCreditCode());
//        obj1.setSubscribeStartTime(param.getSubscribeStartTime());//StringUtils.parseDateTime("2022-08-29 11:24:16")
//        obj1.setSubscribeEndTime(param.getSubscribeEndTime());
//        obj1.setStatus(param.getStatus());
//        req.setTradeSubscribeDetailExecuteTopRequest(obj1);
//
//        AlibabaAlihealthVaccineTradeSubscribeDetailSaveResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//            log.info(String.format("医鹿-预约数据回流返回参数tid = %d response = %s", param.getBizOrderId(), rsp));
//        } catch (ApiException e) {
//            e.printStackTrace();
//            log.error("医鹿--预约数据回流返回错误", e);
//        }
//        return rsp;
//    }
//
//
//    public static void initTmcClient(MessageHandler handler) throws LinkException {
//        TmcClient client = new TmcClient(TaobaoAccountConfig.getInstance().getAppKey(), TaobaoAccountConfig.getInstance().getSecret(), "default");
//        client.setMessageHandler(handler);
//        client.connect(TaobaoAccountConfig.getInstance().getTmcUrl());
//    }
//
//    public static void main(String[] args) {
//        //设置一个sessionKey
//        TaobaoAccountConfig.getInstance().setSessionKey("6102818ca910745c4212eb276810af610394c7dac9192502206467706948");
//
//        Long tid = 2827635987314652858L;//订单ID
//        TradeFullinfoGetResponse tradeFullinfoGetResponse = TaobaoKit.tradeFullinfoGet(SellerUserIdEnum.TIANMAO.userId,tid);
//        if (tradeFullinfoGetResponse != null) {
//            System.out.println(tradeFullinfoGetResponse.getBody());
//        } else {
//            System.out.println("tradeFullinfoGetResponse is null");
//        }
//    }
//
//}
