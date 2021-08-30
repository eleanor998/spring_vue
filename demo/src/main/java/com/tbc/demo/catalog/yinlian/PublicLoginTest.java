package com.tbc.demo.catalog.yinlian;

import com.tbc.demo.utils.RandomValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Slf4j
public class PublicLoginTest {
    private static final String SEND_COUNT = "PUBLICE_LOGIN_COUNT";
    //自己
    private static final String TELPHONE = "17095315205";
/*    //聂姝
    private static final String TELPHONE = "13367276447";*/
/*    //郑吉利
    private static final String TELPHONE = "13272422747";   */
    //刘悦
//    private static final String TELPHONE = "13248080895";

    private static final String COUNT = "-99";
    private static final String SEND_VERIFICODE = "PUBLICE_LOGIN_VERIFICODE";
    private static final String PUBLICE_LOGIN_USER = "PUBLICE_LOGIN_USER";

    String userStr = "{\"accountStatus\":\"ENABLE\",\"activeFlag\":false,\"corpCode\":\"jinj\",\"createBy\":\"f6f085f3d7d04769a4bd8bb25fe13c9f\",\"createTime\":1552632656000,\"dutyName\":\"\",\"email\":\"544535975@qq.com\",\"employeeCode\":\"3428\",\"idCard\":\"\",\"lastModifyBy\":\"be72ea09997f40898dcf8d2f4241beab\",\"lastModifyTime\":1594111853000,\"login\":{\"accountStatus\":\"ENABLE\",\"activationTime\":1567075625000,\"corpCode\":\"jinj\",\"createBy\":\"f6f085f3d7d04769a4bd8bb25fe13c9f\",\"createTime\":1552632657000,\"firstLoginStatus\":\"FALSE\",\"lastModifyBy\":\"be72ea09997f40898dcf8d2f4241beab\",\"lastModifyTime\":1599457982000,\"lastPwdChangeTime\":1597925125000,\"loginId\":\"2afa3044a78945488e9be3144d25e7bb\",\"loginIp\":\"123.178.166.42\",\"loginName\":\"gkk\",\"loginType\":\"APP\",\"optTime\":2,\"password\":\"26000c66140df031373071290be5b44f6f84712c7babc54cb84caae8e96bf5d6\",\"passwordModifyByReset\":true,\"previousLoginTime\":1599536723000,\"userId\":\"be72ea09997f40898dcf8d2f4241beab\",\"userName\":\"gkk\"},\"loginName\":\"gkk\",\"mobile\":\"17095315205\",\"nickName\":\"gkk\",\"optTime\":2,\"organize\":{\"corpCode\":\"jinj\",\"createTime\":1434509108000,\"currentAllUser\":208,\"extendedFieldList\":[],\"isLimit\":true,\"lastModifyBy\":\"23ab835b701243548acdaddb3314c544\",\"lastModifyTime\":1598685626000,\"maxPersonLimit\":200000,\"namePath\":\"时代光华\",\"organizeCode\":\"*\",\"organizeId\":\"94caccd66cf34566a1cbf053334b67f4\",\"organizeName\":\"时代光华\",\"periodUnRepeat\":false,\"showOrder\":1.0},\"organizeId\":\"94caccd66cf34566a1cbf053334b67f4\",\"organizeName\":\"时代光华\",\"positionId\":\"\",\"positionName\":\"\",\"superiorId\":\"\",\"userDetail\":{\"addr\":\"\",\"corpCode\":\"jinj\",\"createBy\":\"f6f085f3d7d04769a4bd8bb25fe13c9f\",\"createTime\":1552632657000,\"degree\":\"\",\"email\":\"544535975@qq.com\",\"ext1\":\"\",\"ext2\":\"\",\"ext22\":\"\",\"ext23\":\"\",\"ext24\":\"\",\"ext26\":\"\",\"ext29\":\"\",\"ext3\":\"\",\"faceUrl\":\"http://null/sf-server/file/getFile/ec9b30510e93565a663f6c4053337e6e-S_1331284919850/5eab8d8f45cec1c3de488db2_0100/tiny\",\"idCard\":\"\",\"lastModifyBy\":\"be72ea09997f40898dcf8d2f4241beab\",\"lastModifyTime\":1595313154000,\"major\":\"\",\"marriage\":\"\",\"mobile\":\"17095315205\",\"nickName\":\"gkk\",\"officeTel\":\"\",\"optTime\":2,\"province\":\"\",\"qq\":\"\",\"rank\":\"\",\"remark\":\"\",\"school\":\"\",\"sex\":\"OTHER\",\"subFileName\":\"5eab8d8f45cec1c3de488db2_0100/x180y0w245h245p1.jpg\",\"userId\":\"be72ea09997f40898dcf8d2f4241beab\",\"wechat\":\"佩恩\"},\"userId\":\"be72ea09997f40898dcf8d2f4241beab\",\"userName\":\"gkk\"}";
    String url = "http://cloud.21tb.com/biz-oim/publicLogin/loginByMobile.do?mobile=MOBILE&verifiCode=VERIFICODE&loginId=LOGINID";
    String cookies = "[{\"name\":\"local_\",\"value\":\"zh_CN\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"changId\",\"value\":\"no\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"oms_eln_session_id\",\"value\":\"37e97291-e42d-4ffd-a8c0-5374323bdacb\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"local_\",\"value\":\"zh_CN\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"href\",\"value\":\"https%3A%2F%2Fcloud.21tb.com%2Frtr%2Fhtml%2Fdesktop%2Fdesktop.index.do\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"accessId\",\"value\":\"f05eae40-9a31-11e5-83f6-57006c315d67\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"bad_idf05eae40-9a31-11e5-83f6-57006c315d67\",\"value\":\"d20c02e2-f241-11ea-999f-c7cdf7fbfc2d\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"nice_idf05eae40-9a31-11e5-83f6-57006c315d67\",\"value\":\"d20c02e3-f241-11ea-999f-c7cdf7fbfc2d\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"nxYongdaoIp\",\"value\":\"172.21.0.18\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"corp_code\",\"value\":\"jinj\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"pageViewNum\",\"value\":\"15\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"invite_user_id\",\"value\":\"\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"loginId\",\"value\":\"Id5e59ccab42f3483caf66c43c2d831391\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"eln_session_id\",\"value\":\"elnSessionId.2fa0dd4173524da182709754c102d776\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"changId\",\"value\":\"79924f585109ade981e2db67272c10f0\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"corpCode\",\"value\":\"jinj\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false},{\"name\":\"userId\",\"value\":\"3d67a18769794b7da6df454f0eacede2\",\"version\":0,\"maxAge\":-1,\"secure\":false,\"httpOnly\":false}]";
    JedisCluster jedis = getJedis();

    public JedisCluster getJedis() {
        return new JedisCluster(new HostAndPort("172.21.0.19", 16382));
    }

    @Test
    public void getResultUrl() {
        String replace = url.replace("MOBILE", "17095315205")
                .replace("VERIFICODE", createVerifiCode("17095315205"))
                .replace("LOGINID", createUser());
        System.out.println(replace);
    }

    @Test
    public void setSendCount() {
        System.out.println("当前手机号已经发送次数:" + jedis.get(SEND_COUNT + TELPHONE));
        jedis.set(SEND_COUNT + TELPHONE, COUNT);
        jedis.set(SEND_VERIFICODE + TELPHONE, "666666");
        System.out.println("设置后手机号已经发送次数:" + jedis.get(SEND_COUNT + TELPHONE));
    }

    private void test1() {

    }

    private String createVerifiCode(String phone) {
        String string = RandomValue.randomNumString(6);
        jedis.set("PUBLICE_LOGIN_VERIFICODE" + phone, string);
        return string;
    }

    private String createUser() {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        jedis.set("PUBLICE_LOGIN_USER" + id, userStr);
        jedis.expire("PUBLICE_LOGIN_USER" + id, 1800);
        return id;
    }

}
