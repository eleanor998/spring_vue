package com.tbc.demo.common.model;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String name;
    private List<String> course;
}
