package com.tbc.demo.catalog.web.service.impl;

import com.tbc.demo.catalog.web.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(String sql) {
        jdbcTemplate.update(sql);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(String sql) {
        jdbcTemplate.update(sql);
        throw new RuntimeException("");
    }
}
