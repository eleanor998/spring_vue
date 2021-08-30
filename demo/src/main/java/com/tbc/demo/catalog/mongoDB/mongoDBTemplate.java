package com.tbc.demo.catalog.mongoDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mongodb")
public class mongoDBTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;


}
