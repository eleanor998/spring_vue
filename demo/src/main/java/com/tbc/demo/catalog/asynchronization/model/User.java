package com.tbc.demo.catalog.asynchronization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String naeme;
    private Boolean sex;
    private int age;
}
