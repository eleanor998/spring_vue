package com.tbc.demo.catalog.mongoDB;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 扩展 Criteria 简化动态参数拼接
 *
 * @author jack
 */
public class CriteriaExpand {

    private Criteria criteria;

    public CriteriaExpand v2Is(String key, Object obj) {
        if (StrUtil.isNotEmpty(key) && obj != null) {
            this.criteria.and(key).is(obj);
        }
        return this;
    }

    public CriteriaExpand v2In(String key, Collection<?> c) {
        if (StringUtils.isNotBlank(key) && CollectionUtils.isNotEmpty(c)) {
            this.criteria.and(key).in(c);
        }
        return this;
    }

    public CriteriaExpand v2Regex(String key, String regexStr) {
        if (!StrUtil.hasBlank(key, regexStr)) {
            criteria.and(key).regex(".*" + regexStr + ".*");
        }
        return this;
    }


    public CriteriaExpand v2GteAndLte(String key, Object start, Object end) {
        if (StrUtil.isNotEmpty(key) && (start != null || end != null)) {
            List<Criteria> criterias = new ArrayList<>();
            if (start != null) {
                criterias.add(Criteria.where(key).gte(start));
            }
            if (end != null) {
                criterias.add(Criteria.where(key).lte(end));
            }
            this.criteria.andOperator(criterias.toArray(new Criteria[0]));
        }
        return this;
    }

    public CriteriaExpand(Criteria criteria) {
        this.criteria = criteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }
}
