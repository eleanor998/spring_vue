//package com.tbc.demo.catalog.caihong;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.jfinal.kit.JsonKit;
//import com.jfinal.kit.StrKit;
//import com.jzdoctor.api.rpc.service.vaccine.ITaobaoTmcRpcService;
//import com.jzdoctor.kit.taobao.TaobaoKit;
//import com.jzdoctor.kit.util.IdempotentKit;
//import com.taobao.api.internal.tmc.Message;
//import com.taobao.api.internal.tmc.MessageHandler;
//import com.taobao.api.internal.tmc.MessageStatus;
//import io.jboot.aop.annotation.Bean;
//import io.jboot.components.rpc.annotation.RPCInject;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Bean(name = "taobaoMessageHandlerListener")
//@Slf4j
//public class TaobaoMessageHandlerListener implements MessageHandler {
//
//    @RPCInject
//    private ITaobaoTmcRpcService taobaoTmcRpcService;
//
//    @Override
//    public void onMessage(Message message, MessageStatus status) throws Exception {
//        try {
//            //处理幂等
//            Map<String, String> hashMap = new HashMap<>();
//            hashMap.put("id", message.getId() + "");
//            hashMap.put("topic", message.getTopic());
//            String hashResponse = IdempotentKit.get("/taobao/tmc", hashMap);
//            if (StrKit.notBlank(hashResponse)) {
//                status.fail();
//                return;
//            }
//
//
//            log.info("message=" + JsonKit.toJson(message));
//            this.taobaoTmcRpcService.saveMessage(message);
//
//            boolean flag = false;
//            log.info("topic=" + message.getTopic());
//            if (TaobaoKit.TaobaoTmcTopicEnum.TRADE_BUYER_PAY.name.equals(message.getTopic())) {
//                flag = this.taobaoTmcRpcService.createOrder(message);
//            }
//            else if (TaobaoKit.TaobaoTmcTopicEnum.TRADE_SUCCESS.name.equals(message.getTopic())) {
//                flag = this.taobaoTmcRpcService.changeOrderVerification(message);
//            }
//            else if (TaobaoKit.TaobaoTmcTopicEnum.REFUND_SUCCESS.name.equals(message.getTopic())) {
//                flag = this.taobaoTmcRpcService.changeOrderRefund(message);
//            }
//            else if (TaobaoKit.TaobaoTmcTopicEnum.TRADE_VACCINE_SUBSCRIBE_CHANGE.name.equals(message.getTopic())) {
//                flag = this.taobaoTmcRpcService.vaccineSubscribeChange(message);
//            }
//            if (flag) {
//                //写缓存--处理幂等
//                IdempotentKit.set("/taobao/tmc", hashMap, JsonKit.toJson(status));
//            }else {
//                log.info("天猫医鹿flag=false");
//                status.fail();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            status.fail(); // 消息处理失败回滚，服务端需要重发
//            // 重试注意：不是所有的异常都需要系统重试。
//            // 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
//            // 对于，由于网络问题，权限问题导致的失败，可重试。
//            // 重试时间 5分钟不等，不要滥用，否则会引起雪崩
//        }
//    }
//}
