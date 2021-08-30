package com.tbc.demo.catalog.mongoDB.Irenshi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Hurlie.Li
 * @date 21-8-16 上午10:09
 * @description:人数统计日报
 */
@Data
@Builder
public class PeopleStatisticsDailyMongoPO {

    @Id
    private String mgid;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * ID
     */
    private String id;

    /**
     * 数据归档时间
     */
    private Date createDate;

    /**
     * 日报归档时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timedailyFilingDate;
    /**
     * 部门时间
     */
    private String datedepartmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 部门编号
     */
    private Integer departNo;
    /**
     * 部门路径
     */
    private String departmentPath;
    /**
     * 在职总数
     */
    private Integer totalNumber;
    /**
     * 动态员工类型
     */
    private Map<String, Integer> staffTypeNumber;

    public static PeopleStatisticsDailyMongoPO createMock() {
        PeopleStatisticsDailyMongoPO build = PeopleStatisticsDailyMongoPO.builder()
                .datedepartmentId("abcd")
                .departmentName("测试部门")
                .departNo(99)
                .departmentPath("/公司/根部门/一级部门/二级部门")
                .totalNumber(9999)
                .timedailyFilingDate(new Date())
                .build();
        Map<String, Integer> map = new HashMap<>();
        map.put("staffType", 123);
        map.put("staffCode", 321);
        map.put("staffName", 456);
        build.setStaffTypeNumber(map);
        build.setCreateDate(new Date());
        build.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        build.setMgid(UUID.randomUUID().toString().replaceAll("-", ""));
        build.setCompanyId("test999");
        return build;
    }

    /**
     * 属性名
     */
    public static class FieldName {
        public static final String ID = "id";
        public static final String TIMEDAILY_FILING_DATE = "timedailyFilingDate";
        public static final String DATEDEPART = "datedepart";
        public static final String DEPARTMENT = "department";
        public static final String DEPARTMENT_ID = "datedepartmentId";
        public static final String TOTAL_NUMB = "totalNumb";
        public static final String STAFF_TYPE = "staffType";
        public static final String mgid = "mgid";
        public static final String COMPANY_ID = "companyId";
        public static final String DEPARTMENT_NAME = "departmentName";
        public static final String CREATE_DATE = "createDate";
        public static final String DAILY_DATE = "dailyDate";
        public static final String DEPARTMENT_IDS = "departmentIds";
        public static final String DEPART_NO = "departNo";
        public static final String SORT_FIELDS = "sortFields";
    }
}

