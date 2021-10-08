package com.tbc.demo.catalog.mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@Data
public class BuliderMongoDB {

    public static final String host = "127.0.0.1";
    public static final int port = 27017;



//    public static final String host = "192.168.1.5";
//    public static final int port = 27017;

//    public static final String host = "192.168.1.210";
//    public static final int port = 27018;

    public static final String databaseName = "lina-hr-db";
    public static final String connection = "peopleStatisticsDaily";


    public MongoClient getClient() {
        //创建mongodb 客户端
        return new MongoClient(host, port);
    }


    public MongoDatabase getDatabase() {
        //创建mongodb 客户端
        MongoClient mongoClient = new MongoClient(host, 27017);
        //连接数据库
        return mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<Document> getCollection() {
        //创建mongodb 客户端
        MongoClient mongoClient = new MongoClient(host, 27017);

        //连接数据库
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        // 连接collection
        return database.getCollection("peopleStatisticsDaily");
    }

    public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(new MongoClient(host, port), databaseName);
    }
}
