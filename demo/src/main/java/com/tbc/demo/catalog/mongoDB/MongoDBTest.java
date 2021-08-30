package com.tbc.demo.catalog.mongoDB;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.tbc.demo.catalog.mongoDB.Irenshi.StaffTurnoverDetailsMongoPO;
import com.tbc.demo.common.model.User;
import com.tbc.demo.utils.RandomValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MongoDBTest {
    private BuliderMongoDB mongo = new BuliderMongoDB();
    private MongoCollection<Document> collection = mongo.getCollection();
    private MongoDatabase database = mongo.getDatabase();
    private String demoCollection = "demo1";
    private MongoTemplate mongoTemplate = mongo.getMongoTemplate();

    @Test
    public void mongoBaseTet() {

        //查询所有集合（表）名称
        MongoIterable<String> strings = database.listCollectionNames();
        log.info("所有表【{}】", JSON.toJSONString(strings));
        //删除表
        database.getCollection(demoCollection).drop();
        //创建一个表
        database.createCollection(demoCollection);
        log.info("所有表【{}】", JSON.toJSONString(strings));
        MongoCollection collection = database.getCollection(demoCollection);
        collection.drop();
        //表内插入数据
        Document document = new Document();
        for (int i = 0; i < 20; i++) {
            document.put("tes3—" + i, i);
        }
        collection.insertOne(document);
        //查询数据
        FindIterable findIterable = collection.find();
        log.info("集合内所有数据 插入后【{}】", JSON.toJSONString(findIterable));
        //删除数据
        //collection.drop();
        //collection.find();
        //log.info("集合内所有数据 删除后【{}】", JSON.toJSONString(findIterable));
        //插入嵌套类型数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test3", 99);
        document.put("tes1—", jsonObject);
        collection.insertOne(document);
        log.info("集合内所有数据 嵌套插入【{}】", JSON.toJSONString(findIterable));
        Document document1 = new Document();
        document1.put("tes1—.test3", 99);
        FindIterable<Document> documents = collection.find(document1);
        log.info("集合内所有数据 嵌套查詢【{}】", JSON.toJSONString(documents));

    }


    @Test
    public void mongoTemplateTest() {
        String key = "username";
        List strs = Arrays.asList("邬咏环");
        if (!StrUtil.hasBlank(key) || !CollectionUtils.isEmpty(strs)) {
            Criteria in = Criteria.where(key).in(strs);
            in.orOperator(Criteria.where("age1").is(0), Criteria.where("age1").is(47));
            List<User> users1 = mongoTemplate.find(new Query(in), User.class);
            System.out.println(users1);
        }

    }

    @Test
    public void mongoTemplateTest2() {
        List<User> users1 = mongoTemplate.find(new Query(new Criteria()), User.class);
        System.out.println(users1);
    }

    @Test
    public void test3() {
        StaffTurnoverDetailsMongoPO mock = StaffTurnoverDetailsMongoPO.createMock();
        mongoTemplate.save(mock, "staffTurnoverDetails");
    }
}
