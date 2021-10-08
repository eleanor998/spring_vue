package com.tbc.demo.catalog.asynchronization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userName;
    private Boolean sex;
    private Integer age;
    private Date dve = new Date();
    private String groupId;
}
