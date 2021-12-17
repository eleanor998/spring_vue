package com.tbc.demo.catalog.asynchronization.model;

import com.tbc.demo.utils.RandomValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;
import java.util.UUID;

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

    public static User mockDate() {
        User user = new User();
        user.setUserName(RandomValue.getChineseName());
        user.setSex(RandomValue.randomSelect(false, true));
        user.setGroupId(UUID.randomUUID().toString());
        return user;
    }
}
