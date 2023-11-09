//package com.tbc.demo.catalog.caihong;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.jfinal.aop.Before;
//import com.jfinal.aop.Inject;
//import com.jfinal.kit.JsonKit;
//import com.jfinal.log.Log;
//import com.jfinal.plugin.activerecord.tx.Tx;
//import com.jzdoctor.api.constants.OrderConstants;
//import com.jzdoctor.api.rpc.param.DeleteRpcParam;
//import com.jzdoctor.api.rpc.result.order.OrderSourceItem;
//import com.jzdoctor.api.rpc.service.order.IOrderRpcService;
//import com.jzdoctor.api.rpc.service.vaccine.ITaobaoTmcRpcService;
//import com.jzdoctor.app.vaccine.model.BlackConfig;
//import com.jzdoctor.app.vaccine.model.TaobaoTmc;
//import com.jzdoctor.app.vaccine.model.VaccineOrder;
//import com.jzdoctor.app.vaccine.model.VaccineOrderItem;
//import com.jzdoctor.app.vaccine.service.IBlackConfigService;
//import com.jzdoctor.app.vaccine.service.ITaobaoTmcService;
//import com.jzdoctor.app.vaccine.service.IVaccineOrderItemService;
//import com.jzdoctor.app.vaccine.service.IVaccineOrderService;
//import com.jzdoctor.app.vaccine.service.param.admin.vaccineOrderV3.*;
//import com.jzdoctor.app.vaccine.service.param.order.CreateVaccineOrderParam;
//import com.jzdoctor.app.vaccine.util.ApplicationConfig;
//import com.jzdoctor.kit.ApplicationServiceInvokeBaseResult;
//import com.jzdoctor.kit.taobao.TaobaoBaseResponse;
//import com.jzdoctor.kit.taobao.TaobaoKit;
//import com.jzdoctor.kit.taobao.TradeVaccineAESUtil;
//import com.jzdoctor.kit.validation.annotation.ChinaMobileValidate;
//import com.taobao.api.domain.Order;
//import com.taobao.api.domain.Trade;
//import com.taobao.api.internal.tmc.Message;
//import com.taobao.api.response.AlibabaAlihealthVaccineTradeOrderChannelGetResponse;
//import com.taobao.api.response.AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse;
//import com.taobao.api.response.TradeFullinfoGetResponse;
//import io.jboot.Jboot;
//import io.jboot.components.rpc.annotation.RPCBean;
//import io.jboot.components.rpc.annotation.RPCInject;
//import io.jboot.db.model.Columns;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//@RPCBean
//public class TaobaoTmcRpcServiceImpl implements ITaobaoTmcRpcService {
//
//    private static final Log log = Log.getLog(TaobaoTmcServiceImpl.class);
//
//    @Inject
//    private ITaobaoTmcService taobaoTmcService;
//
//    @Inject
//    private IVaccineOrderService vaccineOrderService;
//
//    @Inject
//    private IVaccineOrderItemService vaccineOrderItemService;
//
//    @RPCInject
//    private IOrderRpcService orderRpcService;
//
//    @Inject
//    private IBlackConfigService blackConfigService;
//
//
//    @Override
//    public void saveMessage(Message message) {
//        TaobaoTmc entity = new TaobaoTmc();
//        entity.setUserId(message.getUserId());
//        entity.setPubAppKey(message.getPubAppKey());
//        entity.setTopic(message.getTopic());
//        entity.setDataId(message.getDataId());
//        entity.setTid(message.getDataId());
//        entity.setContent(message.getContent());
//        entity.setUpdateDate(new Date());
//        entity.setDeleted(false);
//        this.taobaoTmcService.save(entity);
//    }
//
//    @Override
//    @Before(Tx.class)
//    public boolean createOrder(Message message) {
//        String orderNo = message.getDataId();
//        //判断订单号是否存在getDataId
//        VaccineOrder vaccineOrder = this.vaccineOrderService.getByOrderNo(orderNo);
//        if (vaccineOrder != null) {
//            log.info("淘宝创建订单orderNo已存在：" + orderNo);
//            return true;
//        }
//        //查询淘宝订单详情
//        TradeFullinfoGetResponse tradeFullinfoGetResponse = TaobaoKit.tradeFullinfoGet(message.getUserId(), Long.valueOf(message.getDataId()));
//
//        List<TaobaoBaseResponse<CreateVaccineOrderParam>> resultList = validateAndCreateOrderParam(message, tradeFullinfoGetResponse);
//        if (CollectionUtils.isNotEmpty(resultList)) {
//            List<Boolean> flags = new ArrayList<>();
//            for (int i = 0; i < resultList.size(); i++) {
//                TaobaoBaseResponse<CreateVaccineOrderParam> response = resultList.get(i);
//                if (TaobaoBaseResponse.CodeEnum.SUCCESS.getStatus() == response.getCode()) {
//                    //创建订单(一般只有一个订单)
//                    ApplicationServiceInvokeBaseResult serviceInvokeBaseResult = vaccineOrderService.createOrder(response.getData());
//                    if (!serviceInvokeBaseResult.isSuccess()) {
//                        flags.add(false);
//                    }
//                }
//            }
//            return flags.size() <= 0;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean changeOrderVerification(Message message) {
//        String orderNo = message.getDataId();
//        //判断订单号是否存在
//        VaccineOrder vaccineOrder = vaccineOrderService.getByOrderNo(orderNo);
//        if (vaccineOrder == null) {
//            log.info("淘宝API核销订单orderNo不存在：" + orderNo);
//            return true;
//        }
//        if (!vaccineOrder.getStatus().equals(OrderConstants.VaccineOrderStatus.REGISTERED_WAIT_VERIFY)) {
//            log.error("疫苗订单状态不是待核销状态 orderNo=" + orderNo);
//            return true;
//        }
//
//        //核销
//        VerificationVaineOrderParam param = new VerificationVaineOrderParam();
//        param.setVaccineOrderId(vaccineOrder.getId());
//        param.setAdminUser("淘宝API");
//        vaccineOrderService.verification(param);
//        return true;
//    }
//
//    @Override
//    public boolean changeOrderRefund(Message message) {
//        JSONObject jsonObject = JSONObject.parseObject(message.getContent());
//        String orderNo = jsonObject.getString("tid");
//        if (orderNo == null) {
//            log.info("淘宝API退款orderNo不存在：" + orderNo);
//            return true;
//        }
//
//        VaccineOrder vaccineOrder = vaccineOrderService.getByOrderNo(orderNo);
//        if (vaccineOrder == null) {
//            log.info("淘宝API退款orderNo不存在：" + orderNo);
//            return true;
//        }
//        //关闭订单 --
//        DeleteRpcParam deleteRpcParam = new DeleteRpcParam();
//        deleteRpcParam.setId(vaccineOrder.getId());
//        deleteRpcParam.setAdminUser("淘宝API");
//        ApplicationServiceInvokeBaseResult res = vaccineOrderService.cancelVaccineOrder(deleteRpcParam);
//        if (!res.isSuccess()) {
//            log.info("淘宝API关闭订单失败：" + orderNo);
//            return true;
//        }
//        log.info("淘宝API关闭订单成功:" + orderNo);
//        return true;
//    }
//
//    private List<TaobaoBaseResponse<CreateVaccineOrderParam>> validateAndCreateOrderParam(Message message, TradeFullinfoGetResponse tradeInfo) {
//
//        List<TaobaoBaseResponse<CreateVaccineOrderParam>> resultList = null;
//        Trade trade = tradeInfo.getTrade();//主订单
//        if (CollectionUtils.isNotEmpty(trade.getOrders())) {
//            resultList = new ArrayList<>();
//            for (int i = 0; i < trade.getOrders().size(); i++) {
//                Order order = trade.getOrders().get(i);
//
//                String orderNo = order.getOid() + "";
//                String productName = order.getTitle();
//
//                JSONObject jsonObject = JSONObject.parseObject(order.getOrderAttr());
//                String mobile = jsonObject.getString("mobile");
//
//
//                //需求：天猫渠道过滤黑名单用户
//                if (message.getUserId().equals(TaobaoKit.SellerUserIdEnum.TIANMAO.userId)) {
//                    List<BlackConfig> blackConfigs =  BlackConfig.dao.findListByColumn("type", 1);
//                    List<String> blackMobiles = blackConfigs.stream()
//                            .map(blackConfig -> {
//                                return blackConfig.getParam();
//                            }).collect(Collectors.toList());
//
//                    if (blackMobiles.contains(mobile)){
//                        continue;
//                    }
//                }
//
//
//                if (StringUtils.isBlank(productName) || "null".equals(productName)) {
//                    resultList.add(TaobaoBaseResponse.getNewInstance(TaobaoBaseResponse.CodeEnum.PARAM_ERR.getStatus(),
//                            String.format("订单：%s 商品规格为空", orderNo)));
//                    continue;
//                }
//                if (StringUtils.isBlank(mobile) || "null".equals(mobile)) {
//                    resultList.add(TaobaoBaseResponse.getNewInstance(TaobaoBaseResponse.CodeEnum.PARAM_ERR.getStatus(),
//                            String.format("订单：%s 手机号不能为空", orderNo)));
//                    continue;
//                }
//                if (!ChinaMobileValidate.check(mobile)) {
//                    resultList.add(TaobaoBaseResponse.getNewInstance(TaobaoBaseResponse.CodeEnum.PARAM_ERR.getStatus(),
//                            String.format("订单：%s 手机号格式不正确", orderNo)));
//                    continue;
//                }
//
//
//                //订单来源
//                Integer source = OrderConstants.OrderSource.ALI_FLAGSHIP_ORDER; //天猫
//                if (!message.getUserId().equals(TaobaoKit.SellerUserIdEnum.TIANMAO.userId)) {
//
//                    // 线程休眠，等待数据写入完成
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(500);
//                    } catch (InterruptedException e) {
//                        log.error("线程休眠失败：{}", e);
//                    }
//
//                    //查询医鹿订单来源
//                    AlibabaAlihealthVaccineTradeOrderChannelGetResponse tradeOrderChannelGetResponse = TaobaoKit.tradeOrderChannelGet(message.getUserId(),Long.valueOf(message.getDataId()));
//                    if (message.getUserId().equals(TaobaoKit.SellerUserIdEnum.CAIHONG_YILU.userId)){  //彩虹医鹿商家
//                        if ("微信".equals(tradeOrderChannelGetResponse.getData())){
//                            source = OrderConstants.OrderSource.DOCTOR_DEER_MINIAPP; //医鹿-微信小程序
//                        }else if ("支付宝".equals(tradeOrderChannelGetResponse.getData())){
//                            source = OrderConstants.OrderSource.ALI_PAY; //医鹿支付宝小程序
//                        }else {
//                            source = OrderConstants.OrderSource.DOCTOR_DEER; //医鹿app/其他
//                        }
//                    } else if (message.getUserId().equals(TaobaoKit.SellerUserIdEnum.YUMIAO_YILU.userId)) {  //妤苗医鹿商家
//                        if ("微信".equals(tradeOrderChannelGetResponse.getData())){
//                            source = OrderConstants.OrderSource.YUMIAO_DOCTOR_DEER_MINIAPP; //妤苗医鹿-微信小程序
//                        }else if ("支付宝".equals(tradeOrderChannelGetResponse.getData())){
//                            source = OrderConstants.OrderSource.YUMIAO_ALI_PAY; //妤苗医鹿支付宝小程序
//                        }else {
//                            source = OrderConstants.OrderSource.YUMIAO_DOCTOR_DEER; //妤苗医鹿app/其他
//                        }
//                    }
//                }
//
//                //数据转换
//                CreateVaccineOrderParam param = new CreateVaccineOrderParam();
//                param.setSource(source);
//                param.setSecondSource("");
//                param.setOrderNo(orderNo);
//                param.setOrderTime(trade.getCreated());
//                log.info(String.format("show orderNum oid = %s,num = %s", order.getOid(), order.getNum()));
//                param.setQuantity(order.getNum() == null ? 1 : order.getNum().intValue());
//                param.setStatus(OrderConstants.VaccineOrderStatus.PAID_WAIT_REGISTER);
//                param.setPayOrderNo(orderNo);
//                param.setUserNickname(trade.getBuyerNick());
//                param.setUserMobile(mobile);
//                param.setProductName(productName);
//                param.setSkuNames(order.getSkuPropertiesName());
//                //订单金额 = 预约金金额 + 尾款金额
//                param.setOldAmount(BigDecimal.valueOf(Double.parseDouble(order.getPrice())));
//                //支付金额 = 实付预约金 + 用户实付尾款
//                param.setOldPayAmount(BigDecimal.valueOf(Double.parseDouble(order.getPayment())));
//                param.setAdminUser("淘宝API");
//                //发送登记短信
//                String sendBookSms = Jboot.configValue("taobao.sendBookSms");
//                if (StringUtils.isNotBlank(sendBookSms) && "true".equals(sendBookSms)) {
//                    param.setSendMessage(true);
//                } else {
//                    param.setSendMessage(false);
//                }
//
//                resultList.add(TaobaoBaseResponse.getNewInstance(TaobaoBaseResponse.CodeEnum.SUCCESS, param));
//            }
//        }
//
//        return resultList;
//    }
//
//    @Override
//    public boolean vaccineSubscribeChange(Message message) {
//
//        JSONObject content = JSONObject.parseObject(message.getContent());
//        String messageTyp = content.getString("message_type");
//        if (ObjectUtil.isNull(messageTyp) || (!messageTyp.equals("create"))) {
//            log.info("医鹿预约单变动API不处理消息：" + messageTyp);
//            return true;
//        }
//
//        Long bizOrderId = content.getLong("biz_order_id");
//        String orderNo = String.valueOf(bizOrderId);
//        VaccineOrder vaccineOrder = this.vaccineOrderService.getByOrderNo(orderNo);
//        if (ObjectUtil.isNull(vaccineOrder)) {
//            log.info("医鹿预约单变动API订单orderNo不存在：" + orderNo);
//            return true;
//        }
//
//        //更新接种人信息
//        VaccineOrderItem vaccineOrderItem = vaccineOrderItemService.getFirstByOrderId(vaccineOrder.getId());
//        if (ObjectUtil.isNotNull(vaccineOrderItem) && (!vaccineOrderItem.getArrangeStatus().equals(ApplicationConfig.ArrangeStatus.WAIT_ARRANGE))) {
//            log.info("医鹿预约单接种人状态不是待安排医院，不做覆盖处理 orderNo：" + orderNo);
//            return true;
//        }
//
//
//        //查询医鹿预约单详情
//        Long subscribeId = content.getLong("subscribe_id");
//        Long userId = TaobaoKit.SellerUserIdEnum.TIANMAO.userId;
//        if (vaccineOrder.getSource().equals(OrderConstants.OrderSource.DOCTOR_DEER)
//                || vaccineOrder.getSource().equals(OrderConstants.OrderSource.ALI_PAY)
//                || vaccineOrder.getSource().equals(OrderConstants.OrderSource.DOCTOR_DEER_MINIAPP)){
//            userId = TaobaoKit.SellerUserIdEnum.CAIHONG_YILU.userId;
//        }else if (vaccineOrder.getSource().equals(OrderConstants.OrderSource.YUMIAO_DOCTOR_DEER)
//                || vaccineOrder.getSource().equals(OrderConstants.OrderSource.YUMIAO_ALI_PAY)
//                || vaccineOrder.getSource().equals(OrderConstants.OrderSource.YUMIAO_DOCTOR_DEER_MINIAPP)){
//            userId = TaobaoKit.SellerUserIdEnum.YUMIAO_YILU.userId;
//        }
//
//        log.info("医鹿预约单查询参数 userId：" + userId + " biz_order_id:" + bizOrderId);
//        AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse alibabaAlihealthVaccineTradeSubscribeDetailGetResponse = TaobaoKit.tradeSubscribeDetailGet(userId, bizOrderId, subscribeId);
//        return updateVaccineOrderItem(vaccineOrder,alibabaAlihealthVaccineTradeSubscribeDetailGetResponse);
//    }
//
//    private Boolean updateVaccineOrderItem(VaccineOrder vaccineOrder, AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse tradeSubscribeDetail) {
//
//        log.info("医鹿预约单查询返回：" + JsonKit.toJson(tradeSubscribeDetail));
//
//        List<AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse.TradeVaccineSubscribeDetailTopDTO> list = tradeSubscribeDetail.getData().getDetailTopDtoList();
//        if (ObjectUtil.isEmpty(list)){
//            return true;
//        }
//
//        //取最新一条数据
//        AlibabaAlihealthVaccineTradeSubscribeDetailGetResponse.TradeVaccineSubscribeDetailTopDTO detailTopDTO = list.get(0);
//        if (ObjectUtil.isNull(detailTopDTO)){
//            return true;
//        }
//
//
//        //更新订单状态，以及核销码信息
//        if (vaccineOrder.getStatus().equals(OrderConstants.VaccineOrderStatus.PAID_WAIT_REGISTER)){
//            vaccineOrder.setStatus(OrderConstants.VaccineOrderStatus.REGISTERED_WAIT_VERIFY);
//            vaccineOrder.setBookDate(new Date());
//            vaccineOrder.setCodeNumber("");
//            vaccineOrder.setCodeUrl("");
//        }
//        vaccineOrder.setLastAdminRemark(vaccineOrder.getLastAdminRemark() + " 医鹿API同步预约信息，预约医院：" + detailTopDTO.getPovName() + "，预约开始时间：" + detailTopDTO.getSubscribeStartTime() + " - 预约结束时间：" + detailTopDTO.getSubscribeEndTime());
//        vaccineOrderService.update(vaccineOrder);
//
//
//        //更新接种人信息
//        ApplicationServiceInvokeBaseResult applicationServiceInvokeBaseResult = new ApplicationServiceInvokeBaseResult();
//        VaccineOrderItem vaccineOrderItem = vaccineOrderItemService.getFirstByOrderId(vaccineOrder.getId());
//
//        Integer areaId = 0;
//        if (ObjectUtil.isNotNull(vaccineOrderItem)) {
//            areaId = vaccineOrderItem.getAreaId();
//        }
//
////        log.info("医鹿预约单查询返回姓名：" + TradeVaccineAESUtil.decrypt(detailTopDTO.getUserName(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
////        log.info("医鹿预约单查询返回手机号：" + TradeVaccineAESUtil.decrypt(detailTopDTO.getMobile(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
////        log.info("医鹿预约单查询返回身份证：" + TradeVaccineAESUtil.decrypt(detailTopDTO.getIdentityNo(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//
//        if (ObjectUtil.isNotNull(vaccineOrderItem)){
//            VaccineOrderItemParam vaccineOrderItemParam = new VaccineOrderItemParam();
//            vaccineOrderItemParam.setId(vaccineOrderItem.getId());
//            vaccineOrderItemParam.setAreaId(areaId);
//            vaccineOrderItemParam.setBirthday(null);
//            vaccineOrderItemParam.setName(TradeVaccineAESUtil.decrypt(detailTopDTO.getUserName(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//            vaccineOrderItemParam.setGender(0);
//            vaccineOrderItemParam.setIdcardNo(TradeVaccineAESUtil.decrypt(detailTopDTO.getIdentityNo(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//            vaccineOrderItemParam.setOther("");
//            vaccineOrderItemParam.setMobile(TradeVaccineAESUtil.decrypt(detailTopDTO.getMobile(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//            vaccineOrderItemParam.setAddress("");
//            vaccineOrderItemParam.setAdminUser("医鹿API");
//            vaccineOrderItemParam.setUpdateRecord("医鹿API同步预约人信息");
//
//            applicationServiceInvokeBaseResult = vaccineOrderItemService.edit(vaccineOrderItemParam);
//        }else {
//            AddVaccineItemParam addVaccineItemParam = new AddVaccineItemParam();
//            addVaccineItemParam.setBirthday(null);
//            addVaccineItemParam.setAreaId(areaId);
//            addVaccineItemParam.setName(TradeVaccineAESUtil.decrypt(detailTopDTO.getUserName(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//            addVaccineItemParam.setGender(0);
//            addVaccineItemParam.setIdcardNo(TradeVaccineAESUtil.decrypt(detailTopDTO.getIdentityNo(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//            addVaccineItemParam.setOther("");
//            addVaccineItemParam.setMobile(TradeVaccineAESUtil.decrypt(detailTopDTO.getMobile(),TaobaoKit.AesKeyEnum.AESKEY.aesKey));
//            addVaccineItemParam.setAreaId(0);
//            addVaccineItemParam.setAddress("");
//            addVaccineItemParam.setAdminUser("医鹿API");
//            addVaccineItemParam.setVaccineOrderId(vaccineOrder.getId());
//            applicationServiceInvokeBaseResult = vaccineOrderItemService.add(addVaccineItemParam);
//        }
//
//        if (applicationServiceInvokeBaseResult.isSuccess()) {
//            vaccineOrderItem.setHasVerification(false);
//            vaccineOrderItem.setCheckType(false);
//            vaccineOrderItem.setCodeNumber("");
//            vaccineOrderItem.setCodeUrl("");
//            vaccineOrderItem.setLimitEndDate(null);
//            vaccineOrderItem.setWaitSendTime(null);
//            vaccineOrderItem.setSendTime(null);
//            vaccineOrderItem.setCancelTime(null);
//            vaccineOrderItem.setRefundStatus(0);
//            vaccineOrderItem.setSmsStatus(0);
//            vaccineOrderItem.setSmsMsgid("");
//            vaccineOrderItem.setFollowRemark("[]");
//            vaccineOrderItem.setExtend("{}");
//            vaccineOrderItem.setRefId("");
//            vaccineOrderItem.setHasChangeOrg(false);
//            vaccineOrderItem.setInjectTime(0L);
//            vaccineOrderItem.setTagIds("[]");
//            vaccineOrderItem.setAliPayAccount("");
//            vaccineOrderItem.setAliPayName("");
//            vaccineOrderItemService.update(vaccineOrderItem);
//        }
//
//        return true;
//    }
//}
