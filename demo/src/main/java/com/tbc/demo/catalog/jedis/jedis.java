package com.tbc.demo.catalog.jedis;


import com.tbc.demo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.*;
import java.util.*;

/**
 * 注释
 *
 * @author gekangkang
 * @date 2019/10/31 14:04
 */
@Slf4j
public class jedis {
    Jedis jedis = RedisUtils.getJedis();

    public static void main(String[] args) {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("172.21.0.19", 16383));
        nodes.add(new HostAndPort("172.21.0.19", 16384));
        JedisCluster cluster = new JedisCluster(nodes);
        List<String> list = getList();
        for (String s : list) {
            cluster.del(s);
        }
    }


    /**
     * 测试Hash (map)类型
     */
    @Test
    public void hashTest() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                jedis.hset("test1", "test" + i, "1" + j);
            }
        }
        Set<String> test1 = jedis.hkeys("test1");
        for (String s : test1) {
            String test11 = jedis.hget("test1", s);
            System.out.println(test11);

        }
        System.out.println(test1);
    }

    /**
     * 测试rpush插入顺序与获取顺序
     */
    @Test
    public void listTest() {
        for (int i = 0; i < 10; i++) {
            jedis.rpush("test3", "" + i);
        }
        System.out.println(jedis.llen("test3"));
    }

    //序列化
    private static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] getByte = byteArrayOutputStream.toByteArray();
            return getByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    private static Object unserizlize(byte[] binaryByte) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byteArrayInputStream = new ByteArrayInputStream(binaryByte);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getList() {
        return Arrays.asList("IM_MSG_USMS_MD5_ID_UTN2010233B0915",
                "IM_MSG_USMS_MD5_ID_d720816e70cbffda013c94ff4794bdc5",
                "IM_MSG_USMS_MD5_ID_83bcf86c74488f433a4552d43057d06d",
                "IM_MSG_USMS_MD5_ID_UTN201030AA76DA",
                "IM_MSG_USMS_MD5_ID_7acd517a6723632d7452dd1ec13be9af",
                "IM_MSG_USMS_MD5_ID_UTN201031E86EAE",
                "IM_MSG_USMS_MD5_ID_UTN2010294BAA74",
                "IM_MSG_USMS_MD5_ID_UTN201031DE0F1A",
                "IM_MSG_USMS_MD5_ID_UTN201023E6338A",
                "IM_MSG_USMS_MD5_ID_d9fc4e54681e49843004c041d185c072",
                "IM_MSG_USMS_MD5_ID_e5b887283efd33455566229b5eb66561",
                "IM_MSG_USMS_MD5_ID_UTN20102335F8D9",
                "IM_MSG_USMS_MD5_ID_UTN20103159746B",
                "IM_MSG_USMS_MD5_ID_UTN2010238B72DC",
                "IM_MSG_USMS_MD5_ID_UTN20102955AFD2",
                "IM_MSG_USMS_MD5_ID_UTN2010301C106B",
                "IM_MSG_USMS_MD5_ID_UTN2010300D4C6C",
                "IM_MSG_USMS_MD5_ID_UTN2010293ED37D",
                "IM_MSG_USMS_MD5_ID_UTN201023A79EBD",
                "IM_MSG_USMS_MD5_ID_UTN201023B6E865",
                "IM_MSG_USMS_MD5_ID_baa16c4a2ceb9ad1c0885b8a29e81f30",
                "IM_MSG_USMS_MD5_ID_UTN20103154EE5D",
                "IM_MSG_USMS_MD5_ID_2439cc8f09b61b08f09a2fcfc425a537",
                "IM_MSG_USMS_MD5_ID_UTN201023E8EEF0",
                "IM_MSG_USMS_MD5_ID_UTN201031C5F82C",
                "IM_MSG_USMS_MD5_ID_UTN20103142B56D",
                "IM_MSG_USMS_MD5_ID_d3a5ad5c8242f09d641a4bf308578ca7",
                "IM_MSG_USMS_MD5_ID_UTN20102957C4D7",
                "IM_MSG_USMS_MD5_ID_UTN201030EDA5A0",
                "IM_MSG_USMS_MD5_ID_UTN2010234EDE46",
                "IM_MSG_USMS_MD5_ID_28cabfd92487e176c31b6a42a8e4bc39",
                "IM_MSG_USMS_MD5_ID_812af5413758d1f3a97b630776475815",
                "IM_MSG_USMS_MD5_ID_ebc454a2f49d3e1757025b54e841223c",
                "IM_MSG_USMS_MD5_ID_UTN201023FAB725",
                "IM_MSG_USMS_MD5_ID_UTN2010315C425E",
                "IM_MSG_USMS_MD5_ID_864874b821a38df95bba3d739111fe3b",
                "IM_MSG_USMS_MD5_ID_UTN20102919D296",
                "IM_MSG_USMS_MD5_ID_UTN2010296A2594",
                "IM_MSG_USMS_MD5_ID_8ed99bcdfb98d793cf1b6ca39338a313",
                "IM_MSG_USMS_MD5_ID_UTN20103078C8CF",
                "IM_MSG_USMS_MD5_ID_UTN20102348E5E5",
                "IM_MSG_USMS_MD5_ID_UTN201031265B56",
                "IM_MSG_USMS_MD5_ID_UTN201029CC1203",
                "IM_MSG_USMS_MD5_ID_UTN201023D22905",
                "IM_MSG_USMS_MD5_ID_5b4be862288898e31df2eb15d614968a",
                "IM_MSG_USMS_MD5_ID_7ca352ff19b163d3dac92b48e0d27708",
                "IM_MSG_USMS_MD5_ID_1b449f4e2f145028345adb59442ff5e6",
                "IM_MSG_USMS_MD5_ID_45ae2246381eed5c68cd89122fa46483",
                "IM_MSG_USMS_MD5_ID_d53f0c048be020fc10ba535715ff0d8a",
                "IM_MSG_USMS_MD5_ID_UTN201023407DD7",
                "IM_MSG_USMS_MD5_ID_UTN20102346D959",
                "IM_MSG_USMS_MD5_ID_511f5312d0c3750ffd4fb6b230189fb9",
                "IM_MSG_USMS_MD5_ID_51bba32adf29294635d968e0c401a6bb",
                "IM_MSG_USMS_MD5_ID_9e8b4ea9742206fdc3b278ffefa7141a",
                "IM_MSG_USMS_MD5_ID_UTN2010312FD18A",
                "IM_MSG_USMS_MD5_ID_bcd9516a708dbd57329b91b8733a4a43",
                "IM_MSG_USMS_MD5_ID_dff8be4a075ae8480210981debb9403d",
                "IM_MSG_USMS_MD5_ID_8042de6799c6d034698add7d1978c0fe",
                "IM_MSG_USMS_MD5_ID_1e26f69a7094626f26c8cb9d9586ae17",
                "IM_MSG_USMS_MD5_ID_UTN20103028D6D4",
                "IM_MSG_USMS_MD5_ID_1da0bc7f73ee114913a12b36b9d491cf",
                "IM_MSG_USMS_MD5_ID_44df472787ef5860e00024fda50d9c05",
                "IM_MSG_USMS_MD5_ID_6b1ecb1d1496e09f70ce738b3807d046",
                "IM_MSG_USMS_MD5_ID_4fbb2a01c0ccf8c17dea970870dbaaae",
                "IM_MSG_USMS_MD5_ID_0115d0d939fc40d8fc64728a84e445eb",
                "IM_MSG_USMS_MD5_ID_4e2b05dccc2b0eda3b3af534bf83d170",
                "IM_MSG_USMS_MD5_ID_UTN201030423FDF",
                "IM_MSG_USMS_MD5_ID_UTN2010313B82B0",
                "IM_MSG_USMS_MD5_ID_UTN201023CEBF4B",
                "IM_MSG_USMS_MD5_ID_UTN201031DCAFE1",
                "IM_MSG_USMS_MD5_FAIL_",
                "IM_MSG_USMS_MD5_ID_6228abe179273bcc3e33e34885e10bbf",
                "IM_MSG_USMS_MD5_ID_UTN201031691D9D",
                "IM_MSG_USMS_MD5_ID_6ef5ac03fba33c84f8c01dd35a42812e",
                "IM_MSG_USMS_MD5_ID_UTN201023ECC780",
                "IM_MSG_USMS_MD5_ID_82a3ae944b5471115869d0d16fa6c66b",
                "IM_MSG_USMS_MD5_ID_7cd304846445e6e473b1bac12f5f0bb5",
                "IM_MSG_USMS_MD5_ID_UTN20102388AEB5",
                "IM_MSG_USMS_MD5_ID_UTN201029ABF97D",
                "IM_MSG_USMS_MD5_ID_UTN201030BD1E11",
                "IM_MSG_USMS_MD5_ID_UTN201030EB2E95",
                "IM_MSG_USMS_MD5_ID_4160c40e37d9b5570393ae82794c61c9",
                "IM_MSG_USMS_MD5_ID_77ba0d1c85a97e0435a2dc50d195b8c1",
                "IM_MSG_USMS_MD5_ID_e54d2b0f6be877ac6a7803c2d524ff20",
                "IM_MSG_USMS_MD5_ID_UTN2010319DE4D7",
                "IM_MSG_USMS_MD5_ID479fbea44f15bb2f930e0b9b7dfb0910",
                "IM_MSG_USMS_MD5_ID_43ab695e23c6088a423850e583faa160",
                "IM_MSG_USMS_MD5_ID_UTN2010291DF05C",
                "IM_MSG_USMS_MD5_ID_UTN2010309CBD9F",
                "IM_MSG_USMS_MD5_ID_UTN201030D61E65",
                "IM_MSG_USMS_MD5_ID_34b5f9b9139b25c6f3d064f60483e4b7",
                "IM_MSG_USMS_MD5_ID_UTN201030A35223",
                "IM_MSG_USMS_MD5_ID_UTN2010308FAEFC",
                "IM_MSG_USMS_MD5_ID_b30bd6b42b2f94c178fa4eaa9fd5ad0f",
                "IM_MSG_USMS_MD5_ID_UTN20102302DD3A",
                "IM_MSG_USMS_MD5_ID_bd9b5d6bede3e1de9eca8cb1475d5f38",
                "IM_MSG_USMS_MD5_ID_UTN2010290A1F7A",
                "IM_MSG_USMS_MD5_ID_6515a9ae38bd49ed99bea24ae134a563",
                "IM_MSG_USMS_MD5_ID_ebc16c522ee978e8d5ef9c12758831f1",
                "IM_MSG_USMS_MD5_ID_UTN201031897747",
                "IM_MSG_USMS_MD5_ID_73ee676f2babddd02e6d750baeab85a0",
                "IM_MSG_USMS_MD5_ID_e097bb7b35e65d8708b1c9797bc850ac",
                "IM_MSG_USMS_MD5_ID_UTN2010318FC378",
                "IM_MSG_USMS_MD5_ID_UTN20103185F06E",
                "IM_MSG_USMS_MD5_ID_UTN2010307EA787",
                "IM_MSG_USMS_MD5_ID_be1d33d774b81b395fc4c9e9ea92da3f",
                "IM_MSG_USMS_MD5_ID_UTN20103152DBFB",
                "IM_MSG_USMS_MD5_ID_56d0a230e032ebba2a7c61c4560fc32f",
                "IM_MSG_USMS_MD5_ID_UTN2010234370FE",
                "IM_MSG_USMS_MD5_ID_UTN2010318991F1",
                "IM_MSG_USMS_MD5_IDUTN2010224C301B",
                "IM_MSG_USMS_MD5_IDc990cf668b360773b9b36d6ea67cce14",
                "IM_MSG_USMS_MD5_ID253cfbf157836fa1be43b679b6ce63b3",
                "IM_MSG_USMS_MD5_ID0d096c06a7720c85fa77233eb53d8041",
                "IM_MSG_USMS_MD5_ID_UTN20102344D731",
                "IM_MSG_USMS_MD5_IDUTN2010221601DD",
                "IM_MSG_USMS_MD5_IDUTA201022C4B6F4",
                "IM_MSG_USMS_MD5_ID_dc512dd176e8830ea9775c9e0f9f092a",
                "IM_MSG_USMS_MD5_IDUTN2010224149E7",
                "IM_MSG_USMS_MD5_IDUTN201022AED85F",
                "IM_MSG_USMS_MD5_ID_UTA2010293A17A8",
                "IM_MSG_USMS_MD5_ID7e8807a5eee7a60fbcdd564cc9bdbfa7",
                "IM_MSG_USMS_MD5_ID807e4bc6e1e9e03c19ce4bc54a541f77",
                "IM_MSG_USMS_MD5_ID_UTN20103177633E",
                "IM_MSG_USMS_MD5_ID_UTN2010304C3E72",
                "IM_MSG_USMS_MD5_IDUTA2010221C00C2",
                "IM_MSG_USMS_MD5_ID1f2fbca9a82879ffccd8d2d070b50179",
                "IM_MSG_USMS_MD5_IDUTA2010222DB1FF",
                "IM_MSG_USMS_MD5_IDcb95683eab51977717f32c411706cbd9",
                "IM_MSG_USMS_MD5_ID_UTA2010292B8FC9",
                "IM_MSG_USMS_MD5_ID_UTN201031FDC444",
                "IM_MSG_USMS_MD5_IDUTN20102206E91B",
                "IM_MSG_USMS_MD5_IDUTN201022CE3250",
                "IM_MSG_USMS_MD5_ID_UTN2010234BD1D6",
                "IM_MSG_USMS_MD5_ID_4c7e05e460ac3aaf70b211d5f8ae0eef",
                "IM_MSG_USMS_MD5_ID_e7ff037f6b741e1982210a34beeb4add",
                "IM_MSG_USMS_MD5_ID_UTN201023BD1484",
                "IM_MSG_USMS_MD5_IDUTN2010220F15E0",
                "IM_MSG_USMS_MD5_IDUTN201022D6DCFB",
                "IM_MSG_USMS_MD5_ID_UTN201031F39740",
                "IM_MSG_USMS_MD5_ID_864b834319e0a2c0c14f8b762b1bcdb9",
                "IM_MSG_USMS_MD5_ID_UTN20102328040E",
                "IM_MSG_USMS_MD5_IDUTA201022028E03",
                "IM_MSG_USMS_MD5_ID_UTN201030D7B92F",
                "IM_MSG_USMS_MD5_IDUTN2010229270EB",
                "IM_MSG_USMS_MD5_SUCCESS_",
                "IM_MSG_USMS_MD5_ID_UTN201031760548",
                "IM_MSG_USMS_MD5_IDUTN201022138583",
                "IM_MSG_USMS_MD5_IDUTN20102261C083",
                "IM_MSG_USMS_MD5_ID_UTN2010307621F6",
                "IM_MSG_USMS_MD5_IDUTN201022BF6790",
                "IM_MSG_USMS_MD5_ID243f6d4235f77684312ff031886ce874",
                "IM_MSG_USMS_MD5_IDUTN2010229489C4",
                "IM_MSG_USMS_MD5_IDUTA201022ECA30B",
                "IM_MSG_USMS_MD5_ID_3170fabad8118172e85baff42ca39e86",
                "IM_MSG_USMS_MD5_ID_UTN201031B7A9FD",
                "IM_MSG_USMS_MD5_IDc07591db231e75593f6266ff74549031",
                "IM_MSG_USMS_MD5_IDUTN2010224339CC",
                "IM_MSG_USMS_MD5_IDdff8be4a075ae8480210981debb9403d",
                "IM_MSG_USMS_MD5_ID03da0574efc0d7658d44c0adcb68cb58",
                "IM_MSG_USMS_MD5_IDUTN2010225F9B1B",
                "IM_MSG_USMS_MD5_IDUTA201022B87767",
                "IM_MSG_USMS_MD5_ID_UTN20103015F39D",
                "IM_MSG_USMS_MD5_ID_UTN2010231C8B47",
                "IM_MSG_USMS_MD5_IDUTN2010226B7A0E",
                "IM_MSG_USMS_MD5_IDe54d2b0f6be877ac6a7803c2d524ff20",
                "IM_MSG_USMS_MD5_IDUTN201022275990",
                "IM_MSG_USMS_MD5_ID_UTN201023AA2CCD",
                "IM_MSG_USMS_MD5_ID_UTN201023D8C85D",
                "IM_MSG_USMS_MD5_ID5dd216a08e865db463417d79224ef7fd",
                "IM_MSG_USMS_MD5_IDUTN201022E6375B",
                "IM_MSG_USMS_MD5_IDUTN201022AC01B9",
                "IM_MSG_USMS_MD5_IDd37041310b90b19524c3ad7d318f778f",
                "IM_MSG_USMS_MD5_IDaf71bb28c19d4742f02cbeaf8cba74f6",
                "IM_MSG_USMS_MD5_ID4c7e05e460ac3aaf70b211d5f8ae0eef",
                "IM_MSG_USMS_MD5_IDe2a32a17849ac8da8513c39eaf32170e",
                "IM_MSG_USMS_MD5_ID24e3d5edfd1fe53f6a6ed19a0e61e52d",
                "IM_MSG_USMS_MD5_ID_52666856872a12bb98bcb47200dd6bc3",
                "IM_MSG_USMS_MD5_IDUTN20102286D6A0",
                "IM_MSG_USMS_MD5_ID_af71bb28c19d4742f02cbeaf8cba74f6",
                "IM_MSG_USMS_MD5_ID_UTN20102971EB86",
                "IM_MSG_USMS_MD5_ID_UTN201029FFD4BE",
                "IM_MSG_USMS_MD5_ID_82f3784666efb71ac5957d6ec99fd577",
                "IM_MSG_USMS_MD5_ID_UTN201031309705",
                "IM_MSG_USMS_MD5_ID941402c7edfc2bec3fce6c19bbde0656",
                "IM_MSG_USMS_MD5_IDd3a5ad5c8242f09d641a4bf308578ca7",
                "IM_MSG_USMS_MD5_ID1fd2feb1c0b95e1de8a79ebb39c34f32",
                "IM_MSG_USMS_MD5_ID_UTN20103064C5AA",
                "IM_MSG_USMS_MD5_ID_7f3169ab986d861b7137252abcad77c2",
                "IM_MSG_USMS_MD5_IDUTN2010220FA8F1",
                "IM_MSG_USMS_MD5_ID_UTN2010236A8AA1",
                "IM_MSG_USMS_MD5_ID_29161a6b01cf4cbc47f507705b04ebcb",
                "IM_MSG_USMS_MD5_ID39f0187e540f5299393313cc9ff5a8ea",
                "IM_MSG_USMS_MD5_ID59101664c9ec0325e73bf8f7be020270",
                "IM_MSG_USMS_MD5_ID_UTN201023EA443C",
                "IM_MSG_USMS_MD5_ID_a836a51917d0ad5d955415f59648dee2",
                "IM_MSG_USMS_MD5_IDebc454a2f49d3e1757025b54e841223c",
                "IM_MSG_USMS_MD5_ID_UTN201030CCB856",
                "IM_MSG_USMS_MD5_IDa10472f62a9b4c69724072aac69d062d",
                "IM_MSG_USMS_MD5_ID_382f887a4c68a8452b36a51e53c8310b",
                "IM_MSG_USMS_MD5_ID_270f30bc5da581787b0fc975a5d0ff57",
                "IM_MSG_USMS_MD5_ID_UTN201029228B4D",
                "IM_MSG_USMS_MD5_ID90c146411046b57068017d17ef2b5268",
                "IM_MSG_USMS_MD5_ID_UTN201023673E0D",
                "IM_MSG_USMS_MD5_IDUTN201022166D2C",
                "IM_MSG_USMS_MD5_ID_UTN2010238EC033",
                "IM_MSG_USMS_MD5_ID_81bca20eed4c21856e92659bb8f0426a",
                "IM_MSG_USMS_MD5_IDUTA2010224E4256",
                "IM_MSG_USMS_MD5_ID_UTN20102323CB25",
                "IM_MSG_USMS_MD5_IDUTN20102276FF14",
                "IM_MSG_USMS_MD5_ID_UTN2010296B7C66",
                "IM_MSG_USMS_MD5_IDUTN201022918461",
                "IM_MSG_USMS_MD5_ID_UTN2010230E60A6",
                "IM_MSG_USMS_MD5_IDUTN201022D593B6",
                "IM_MSG_USMS_MD5_ID34ff8b980a9549fd8baf5d564fcb1689",
                "IM_MSG_USMS_MD5_IDUTN2010229957D6",
                "IM_MSG_USMS_MD5_ID7acd517a6723632d7452dd1ec13be9af",
                "IM_MSG_USMS_MD5_ID_1fd2feb1c0b95e1de8a79ebb39c34f32",
                "IM_MSG_USMS_MD5_ID_UTN2010314A4645",
                "IM_MSG_USMS_MD5_ID4fa2fe7953c9cbd0d0551ed2d64d93bc",
                "IM_MSG_USMS_MD5_ID_UTN201030283B71",
                "IM_MSG_USMS_MD5_ID_UTN201030EA1BF3",
                "IM_MSG_USMS_MD5_IDb26f778583687683f23e20385e195970",
                "IM_MSG_USMS_MD5_ID_UTN20103128F84E",
                "IM_MSG_USMS_MD5_IDUTN2010221FB4AF",
                "IM_MSG_USMS_MD5_IDUTN201022C23681",
                "IM_MSG_USMS_MD5_IDUTN20102260653A",
                "IM_MSG_USMS_MD5_ID270f30bc5da581787b0fc975a5d0ff57",
                "IM_MSG_USMS_MD5_ID_251d101268c9ffc1170fa166bcc380a2",
                "IM_MSG_USMS_MD5_ID2bb3784b490de1e09fd1ef01e5dc8fac",
                "IM_MSG_USMS_MD5_ID52666856872a12bb98bcb47200dd6bc3",
                "IM_MSG_USMS_MD5_IDUTN20102268DBCA",
                "IM_MSG_USMS_MD5_ID_0d096c06a7720c85fa77233eb53d8041",
                "IM_MSG_USMS_MD5_IDfa9f070a2b2032c78b956d62d501e1f3",
                "IM_MSG_USMS_MD5_IDUTN20102267D089",
                "IM_MSG_USMS_MD5_IDUTN201022320927",
                "IM_MSG_USMS_MD5_ID_d2985c4584f488a7c61aa2d82e7e6f4a",
                "IM_MSG_USMS_MD5_IDUTN201022E0106B",
                "IM_MSG_USMS_MD5_ID_UTN201030EEA6CD",
                "IM_MSG_USMS_MD5_ID_UTN201031583EE0",
                "IM_MSG_USMS_MD5_IDUTN201022919196",
                "IM_MSG_USMS_MD5_ID_UTN201023A467EA",
                "IM_MSG_USMS_MD5_ID_788d368590658c312f7377208b7ca079",
                "IM_MSG_USMS_MD5_ID_UTN201029DBA31C",
                "IM_MSG_USMS_MD5_ID864b834319e0a2c0c14f8b762b1bcdb9",
                "IM_MSG_USMS_MD5_ID64e6bff369983e0be79f44fe84962705",
                "IM_MSG_USMS_MD5_ID251d101268c9ffc1170fa166bcc380a2",
                "IM_MSG_USMS_MD5_ID_a59906e87723e5aec136f62114b73988",
                "IM_MSG_USMS_MD5_ID1c3ba2f51732aa3161d64e3103e2683e",
                "IM_MSG_USMS_MD5_ID19e528ccc8bc3decad0110bf50889f34",
                "IM_MSG_USMS_MD5_IDUTN201022C80FDF",
                "IM_MSG_USMS_MD5_ID70d27bfc213782d403cf98c01eb4a070",
                "IM_MSG_USMS_MD5_IDUTN20102299F2DA",
                "IM_MSG_USMS_MD5_IDUTN201022AF1CB8",
                "IM_MSG_USMS_MD5_ID_UTN201029F72324",
                "IM_MSG_USMS_MD5_IDUTN2010224BF959",
                "IM_MSG_USMS_MD5_ID29161a6b01cf4cbc47f507705b04ebcb",
                "IM_MSG_USMS_MD5_IDUTN201022D970C2",
                "IM_MSG_USMS_MD5_ID_UTA2010311550B6",
                "IM_MSG_USMS_MD5_IDf3769e83d8c39e72a9420200554e1a17",
                "IM_MSG_USMS_MD5_IDUTN2010227A1E83",
                "IM_MSG_USMS_MD5_IDUTN201022197C25",
                "IM_MSG_USMS_MD5_IDUTN201022F1C625",
                "IM_MSG_USMS_MD5_ID5d31183a437ba48e9ce3b8cd2fbbb770",
                "IM_MSG_USMS_MD5_ID49e220239efc3f17e71841dd7a5c6822",
                "IM_MSG_USMS_MD5_ID6b037b28669d91e22645ba805ee07726",
                "IM_MSG_USMS_MD5_IDUTN201023D5D266",
                "IM_MSG_USMS_MD5_IDUTN2010236DEE95",
                "IM_MSG_USMS_MD5_ID_UTN2010230A2A21",
                "IM_MSG_USMS_MD5_ID_UTN201031F01FA7",
                "IM_MSG_USMS_MD5_ID_UTN20103172479F",
                "IM_MSG_USMS_MD5_IDUTA2010222FB7CA",
                "IM_MSG_USMS_MD5_ID4a2fae1444ffe8d495fec31a7d81d9e5",
                "IM_MSG_USMS_MD5_IDd2985c4584f488a7c61aa2d82e7e6f4a",
                "IM_MSG_USMS_MD5_IDUTN20102235EA4C",
                "IM_MSG_USMS_MD5_ID8c2e619187b49650ebb76abeeee73222",
                "IM_MSG_USMS_MD5_ID_UTA201029775E77",
                "IM_MSG_USMS_MD5_IDUTN2010223A9305",
                "IM_MSG_USMS_MD5_IDaed0f62984f8cea769b9466bdbe4ffb8",
                "IM_MSG_USMS_MD5_IDd9d037692ea6500a5b00b44be20a9758",
                "IM_MSG_USMS_MD5_ID4160c40e37d9b5570393ae82794c61c9",
                "IM_MSG_USMS_MD5_IDefd4a710982c28bb01867a595fa99314",
                "IM_MSG_USMS_MD5_ID_f517f8e1be3027043c79c6a1f06f93fd",
                "IM_MSG_USMS_MD5_ID679f6b8f2b722467b55d8a5510ca46d9",
                "IM_MSG_USMS_MD5_ID_4fb20c6506483b7a95d3805cf2d40ab2",
                "IM_MSG_USMS_MD5_IDUTN20102256B22E",
                "IM_MSG_USMS_MD5_IDUTN2010220E8D83",
                "IM_MSG_USMS_MD5_IDUTN201022694432",
                "IM_MSG_USMS_MD5_ID3170fabad8118172e85baff42ca39e86",
                "IM_MSG_USMS_MD5_ID_992b8b57aad67435b919a7603e6a3a42",
                "IM_MSG_USMS_MD5_ID_UTA201029178ACF",
                "IM_MSG_USMS_MD5_ID071da1cb594e650966d4d4c74d200650",
                "IM_MSG_USMS_MD5_IDUTN201022F8C4E3",
                "IM_MSG_USMS_MD5_ID73ee676f2babddd02e6d750baeab85a0",
                "IM_MSG_USMS_MD5_IDUTN201022F9B567",
                "IM_MSG_USMS_MD5_IDa7ca80ec6f90ba08cf2af3b136f2df77",
                "IM_MSG_USMS_MD5_ID_af0e06a01a968805647d06ba7be5fe26",
                "IM_MSG_USMS_MD5_IDUTN201022EE1986",
                "IM_MSG_USMS_MD5_ID1b449f4e2f145028345adb59442ff5e6",
                "IM_MSG_USMS_MD5_ID_6fd14ee14ce74c3b308313c8b182ced8",
                "IM_MSG_USMS_MD5_ID_UTN201023A31C63",
                "IM_MSG_USMS_MD5_IDa734e9f08336a9f8d0c6980f1439c48f",
                "IM_MSG_USMS_MD5_IDd4f1b86069c455e7cac3efefefd4b718",
                "IM_MSG_USMS_MD5_IDUTN2010229172EB",
                "IM_MSG_USMS_MD5_ID_cb95683eab51977717f32c411706cbd9",
                "IM_MSG_USMS_MD5_IDUTN2010221F1102",
                "IM_MSG_USMS_MD5_ID_UTN201031BAB912",
                "IM_MSG_USMS_MD5_IDUTN2010229A2998",
                "IM_MSG_USMS_MD5_IDf5c61e0fe5eba822022a72554fecf72b",
                "IM_MSG_USMS_MD5_ID7345e435ac63cbec10f0596c59494e18",
                "IM_MSG_USMS_MD5_ID_4fa2fe7953c9cbd0d0551ed2d64d93bc",
                "IM_MSG_USMS_MD5_ID_f3769e83d8c39e72a9420200554e1a17",
                "IM_MSG_USMS_OPEN_CORP_LIST",
                "IM_MSG_USMS_MD5_ID_UTA201031943277",
                "IM_MSG_USMS_MD5_IDUTN20102234F4CF",
                "IM_MSG_USMS_MD5_ID_64e6bff369983e0be79f44fe84962705",
                "IM_MSG_USMS_MD5_ID_UTN2010235B2F11",
                "IM_MSG_USMS_MD5_ID992b8b57aad67435b919a7603e6a3a42",
                "IM_MSG_USMS_MD5_IDa8b4dd2f17cbc5b6eec5c3b9ce1de63a",
                "IM_MSG_USMS_MD5_ID_UTN201029C0F020",
                "IM_MSG_USMS_MD5_ID5fd5b9a055fa91985bc1fe4e6f0efd8e",
                "IM_MSG_USMS_MD5_ID8f41fbef5f574e22eb2bb2fce34ad031",
                "IM_MSG_USMS_MD5_ID_d6b226c5e79a9b187df525e7a8ce8cae",
                "IM_MSG_USMS_MD5_ID_UTA201031F7A6C1",
                "IM_MSG_USMS_MD5_ID_UTA2010300D6403",
                "IM_MSG_USMS_MD5_IDUTN201022861968");
    }

}
