package com.tbc.demo.catalog.mongoDB;

import com.alibaba.fastjson.JSON;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;

import com.tbc.demo.catalog.asynchronization.model.User;
import com.tbc.demo.utils.RandomValue;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

public class MongodbTemplate {


    /**
     * 测试monggo 分组排序 ,结果映射有问题，
     *
     * @return
     */
    @Test
    public void test() {
        //获取mongo链接
        BuliderMongoDB buliderMongoDB = new BuliderMongoDB();
        MongoTemplate mongoTemplate = buliderMongoDB.getMongoTemplate();
        //清空旧数据
        mongoTemplate.remove(new Query(), BuliderMongoDB.databaseName);
        //插入测试数据
        List<User> testData = getTestData(60);
        mongoTemplate.insert(testData, BuliderMongoDB.databaseName);
        List<User> users = mongoTemplate.find(new Query(), User.class);
        System.out.println(JSON.toJSONString(users));
        //多分组排序 USER 是查询的实体
        TypedAggregation<User> groupQuery = Aggregation.newAggregation(User.class
                //排序字段，排序规则
                //传入查询条件 Criteria可以嵌套组合
                //分组字段
                , Aggregation.group("groupId")
                //分页offset
                , Aggregation.skip(1L)
                //分页size
                , Aggregation.limit(10));
        AggregationResults<User> aggregate = mongoTemplate.aggregate(groupQuery, BuliderMongoDB.databaseName, User.class);
        List<User> mappedResults = aggregate.getMappedResults();
        System.out.println(JSON.toJSONString(mappedResults));
    }


    /**
     * 测试monggo 分组排序 ,结果映射有问题， 多次排序  1. 先使用第一次排序的结果进行排序 2. 在不改变上一次的结果的前提下再次排序
     *
     * @return
     */
    @Test
    public void test2() {
        //获取mongo链接
        BuliderMongoDB buliderMongoDB = new BuliderMongoDB();
        MongoTemplate mongoTemplate = buliderMongoDB.getMongoTemplate();
        Query query = new Query();
        query.with(Sort.by(getSortList()));
        List<User> ts = mongoTemplate.find(query, User.class, BuliderMongoDB.databaseName);
        for (User t : ts) {
            System.out.println(t);
        }
    }

    public static List<User> getTestData(int size) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String uid = UUID.randomUUID().toString().replace("-", "");
            User befor = new User();
            User after = new User();
            befor.setAge(i);
            befor.setUserName(RandomValue.getChineseName());
            befor.setGroupId(uid);
            befor.setSex(System.currentTimeMillis() % 1 == 0);
            after.setAge(i);
            after.setUserName(RandomValue.getChineseName());
            after.setGroupId(uid);
            after.setSex(System.currentTimeMillis() % 1 == 0);
            list.add(befor);
            list.add(after);
        }
        return list;
    }

    /**
     * 排序
     *
     * @return
     */
    private List<Sort.Order> getSortList() {
        //多字段排序
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "groupId");
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "age");
        return Arrays.asList(order1, order);
    }
}
