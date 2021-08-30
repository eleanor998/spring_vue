package com.tbc.demo.catalog.mongoDB.Irenshi;

import com.alibaba.fastjson.annotation.JSONField;
import com.tbc.demo.utils.RandomValue;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Hurlie.Li
 * @date 21-8-16 上午9:47
 * @description:人员流动明细
 */
@Data
public class StaffTurnoverDetailsMongoPO {


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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String userId;
    /**
     * 员工ID
     */
    private String staffId;

    /**
     * 姓名
     */
    private String staffName;

    /**
     * 工号
     */
    private String staffNo;

    /**
     * 手机号码
     */
    private String mobileNo;

    /**
     * 员工类型
     */
    private String staffType;

    /**
     * 员工状态
     */
    private String staffStatus;

    /**
     * 试用期状态
     */
    private String probationStatus;

    /**
     * 合同公司ID
     */
    private String corporationId;

    /**
     * 合同公司
     */
    private String corporationName;

    /**
     * 工作地点ID
     */
    private String companySiteId;

    /**
     * 工作地点名称
     */
    private String companySiteName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门
     */
    private String departmentName;

    /**
     * 部门编码
     */
    private String departNo;

    /**
     * 部门路径
     */
    private String departmentPath;

    /**
     * 职位ID
     */
    private String positionId;

    /**
     * 职位
     */
    private String positionName;

    /**
     * 职务ID
     */
    private String jobTitleId;

    /**
     * 职务
     */
    private String jobTitleName;

    /**
     * 职务分类
     */
    private String jobCategoryName;

    /**
     * 职级ID
     */
    private String positionLevelId;

    /**
     * 职级
     */
    private String positionLevelName;

    /**
     * 一级部门ID
     */
    private String firstLevelDepartmentId;

    /**
     * 一级部门名称
     */
    private String firstLevelDepartmentName;

    /**
     * 二级部门名称
     */
    private String secondaryDepartmentName;

    /**
     * 二级部门ID
     */
    private String secondaryDepartmentId;

    /**
     * 三级部门ID
     */
    private String tertiaryDepartmentId;

    /**
     * 三级部门名称
     */
    private String tertiaryDepartmentName;

    /**
     * 四级部门ID
     */
    private String fourLevelDepartmentId;

    /**
     * 四级部门名称
     */
    private String fourLevelDepartmentName;

    /**
     * 五级部门Id
     */
    private String fiveLevelDepartmentId;

    /**
     * 五级部门名称
     */
    private String fiveLevelDepartmentName;

    /**
     * 六级部门ID
     */
    private String sixthLevelDepartmentId;

    /**
     * 六级部门名称
     */
    private String sixthLevelDepartmentName;

    /**
     * 七级部门ID
     */
    private String sevenLevelDepartmentId;

    /**
     * 七级部门名称
     */
    private String sevenLevelDepartmentName;

    /**
     * 八级部门ID
     */
    private String eightLevelDepartmentId;

    /**
     * 八级部门名称
     */
    private String eightLevelDepartmentName;

    /**
     * 九级部门ID
     */
    private String nineLevelDepartmentId;

    /**
     * 九级部门名称
     */
    private String nineLevelDepartmentName;

    /**
     * 十级部门ID
     */
    private String tenLevelDepartmentId;

    /**
     * 十级部门名称
     */
    private String tenLevelDepartmentName;

    /**
     * 部门类型
     */
    private String organizationChartNodeType;

    /**
     * 所属公司
     */
    private String subCompanyName;

    /**
     * 部门异动项
     */
    private Boolean departmentChangeItem;

    /**
     * 部门路径异动项
     */
    private Boolean departmentPathChangeItem;

    /**
     * 职位异动项
     */
    private Boolean positionChangeItem;

    /**
     * 员工类型异动项
     */
    private Boolean staffTypeChangeItem;

    /**
     * 职务分类异动项
     */
    private Boolean jobCategoryChangeItem;

    /**
     * 职务异动项
     */
    private Boolean jobTitleChangeItem;

    /**
     * 职级异动项
     */
    private Boolean positionLevelChangeItem;

    /**
     * 流向
     * INFLOW流入 OUTFLOW流出
     */
    private String flowDirection;

    /**
     * 异动类型
     * ENTRY入职 TRANSFER调动 QUIT离职 ORGANIZATIONAL_ADJUSTMENT组织调整 POSITION_ADJUSTMENT职位调整
     */
    private String changeType;

    /**
     * 异动子类
     */
    private String changeSubclass;

    /**
     * 生效时间
     */
    private Date effectiveDate;

    /**
     * 数据来源
     */
    private String source;

    public static StaffTurnoverDetailsMongoPO createMock() {
        StaffTurnoverDetailsMongoPO staffTurnoverDetailsMongoPO = new StaffTurnoverDetailsMongoPO();
        staffTurnoverDetailsMongoPO.setStaffId(UUID.randomUUID().toString().replaceAll("-", ""));
        staffTurnoverDetailsMongoPO.setStaffName("测试" + RandomValue.randomNumString(3));
        staffTurnoverDetailsMongoPO.setStaffNo("TEST" + RandomValue.randomNumString(3));
        staffTurnoverDetailsMongoPO.setMobileNo("13" + RandomValue.randomNumString(10));
        staffTurnoverDetailsMongoPO.setStaffType(RandomValue.randomSelect("INTERSHIP"));
        staffTurnoverDetailsMongoPO.setStaffStatus("在职");
        staffTurnoverDetailsMongoPO.setProbationStatus("转正");
        staffTurnoverDetailsMongoPO.setCorporationId("TEST_CORP_ID");
        staffTurnoverDetailsMongoPO.setCorporationName("TEST——CorporationName");
        staffTurnoverDetailsMongoPO.setCompanySiteId("TEST_SiteId");
        staffTurnoverDetailsMongoPO.setCompanySiteName("TEST——SiteName");
        staffTurnoverDetailsMongoPO.setDepartmentId("987654");
        staffTurnoverDetailsMongoPO.setDepartmentName("三级部门");
        staffTurnoverDetailsMongoPO.setDepartNo(RandomValue.randomNumString(16));
        staffTurnoverDetailsMongoPO.setDepartmentPath("/一级部门/二级部门/三级部门");
        staffTurnoverDetailsMongoPO.setPositionId("TEST——PositionId");
        staffTurnoverDetailsMongoPO.setPositionName("产品");
        staffTurnoverDetailsMongoPO.setJobTitleId(RandomValue.randomNumString(16));
        staffTurnoverDetailsMongoPO.setJobTitleName("测试" + RandomValue.randomNumString(3));
        staffTurnoverDetailsMongoPO.setJobCategoryName("测试部" + RandomValue.randomNumString(3));
        staffTurnoverDetailsMongoPO.setPositionLevelId(RandomValue.randomNumString(16));
        staffTurnoverDetailsMongoPO.setPositionLevelName("TEST_PositionLevelName");
        staffTurnoverDetailsMongoPO.setFirstLevelDepartmentId(RandomValue.randomNumString(16));
        staffTurnoverDetailsMongoPO.setFirstLevelDepartmentName("一级部门");
        staffTurnoverDetailsMongoPO.setSecondaryDepartmentName("二级部门");
        staffTurnoverDetailsMongoPO.setSecondaryDepartmentId("123");
        staffTurnoverDetailsMongoPO.setTertiaryDepartmentId("654");
        staffTurnoverDetailsMongoPO.setTertiaryDepartmentName("三级部门");
        staffTurnoverDetailsMongoPO.setFourLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setFourLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setFiveLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setFiveLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setSixthLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setSixthLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setSevenLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setSevenLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setEightLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setEightLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setNineLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setNineLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setTenLevelDepartmentId("");
        staffTurnoverDetailsMongoPO.setTenLevelDepartmentName("");
        staffTurnoverDetailsMongoPO.setOrganizationChartNodeType("organization_structure_type.enum.company");
        staffTurnoverDetailsMongoPO.setSubCompanyName("");
        staffTurnoverDetailsMongoPO.setDepartmentChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setDepartmentPathChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setPositionChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setStaffTypeChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setJobCategoryChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setJobTitleChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setPositionLevelChangeItem(RandomUtils.nextBoolean());
        staffTurnoverDetailsMongoPO.setFlowDirection(RandomValue.randomSelect("INFLOW", "OUTFLOW"));
        staffTurnoverDetailsMongoPO.setChangeType(RandomValue.randomSelect("ENTRY", "TRANSFER", "QUIT", "ORGANIZATIONAL_ADJUSTMENT", "POSITION_ADJUSTMENT"));
        staffTurnoverDetailsMongoPO.setChangeSubclass(RandomValue.randomSelect("ENTRY", "ORGANIZATION_MERGER", "ORGANIZATIONAL_CHANGE", "JOB_CONSOLIDATION", "Enum.transferMode", "Enum.QuitType"));
        staffTurnoverDetailsMongoPO.setEffectiveDate(cn.hutool.core.util.RandomUtil.randomDay(-600, 60));
        staffTurnoverDetailsMongoPO.setSource("TEST_SOURCE");
        staffTurnoverDetailsMongoPO.setMgid(RandomValue.randomNumString(16));
        staffTurnoverDetailsMongoPO.setCompanyId(RandomValue.randomSelect("c81fcf0ab7b2451ebd307fca730cb1c9"));
        staffTurnoverDetailsMongoPO.setId(RandomValue.randomNumString(16));
        staffTurnoverDetailsMongoPO.setCreateDate(cn.hutool.core.util.RandomUtil.randomDay(-600, 60));
        List<String> strings4 = Arrays.asList("user1", "user2", "94b24028be0e44479c133033d57db518");
        staffTurnoverDetailsMongoPO.setUserId(strings4.get(Integer.parseInt(RandomValue.randomNumString(2)) % 3));
        return staffTurnoverDetailsMongoPO;
    }

    public static class FieldName {
        public static String STAFF_ID = "staffId";
        public static String STAFF_NAME = "staffName";
        public static String STAFF_NO = "staffNo";
        public static String MOBILE_NO = "mobileNo";
        public static String STAFF_TYPE = "staffType";
        public static String STAFF_STATUS = "staffStatus";
        public static String PROBATION_STATUS = "probationStatus";
        public static String CORPORATION_ID = "corporationId";
        public static String CORPORATION_NAME = "corporationName";
        public static String COMPANYSITE_ID = "companySiteId";
        public static String COMPANYSITE_NAME = "companySiteName";
        public static String DEPARTMENT_ID = "departmentId";
        public static String DEPARTMENT_NAME = "departmentName";
        public static String DEPART_NO = "departNo";
        public static String DEPARTMENT_PATH = "departmentPath";
        public static String POSITION_ID = "positionId";
        public static String POSITION_NAME = "positionName";
        public static String JOBTITLE_ID = "jobTitleId";
        public static String JOBTITLE_NAME = "jobTitleName";
        public static String JOBCATEGORY_NAME = "jobCategoryName";
        public static String POSITION_LEVEL_ID = "positionLevelId";
        public static String POSITION_LEVEL_NAME = "positionLevelName";
        public static String FIRSTLEVEL_DEPARTMENT_ID = "firstLevelDepartmentId";
        public static String FIRSTLEVEL_DEPARTMENT_NAME = "firstLevelDepartmentName";
        public static String SECONDARY_DEPARTMENT_NAME = "secondaryDepartmentName";
        public static String SECONDARY_DEPARTMENT_ID = "secondaryDepartmentId";
        public static String TERTIARY_DEPARTMENT_ID = "tertiaryDepartmentId";
        public static String TERTIARY_DEPARTMENT_NAME = "tertiaryDepartmentName";
        public static String FOURLEVEL_DEPARTMENT_ID = "fourLevelDepartmentId";
        public static String FOURLEVEL_DEPARTMENT_NAME = "fourLevelDepartmentName";
        public static String FIVELEVEL_DEPARTMENT_ID = "fiveLevelDepartmentId";
        public static String FIVELEVEL_DEPARTMENT_NAME = "fiveLevelDepartmentName";
        public static String SIXTHLEVE_LDEPARTMENT_ID = "sixthLevelDepartmentId";
        public static String SIXTHLEVEL_DEPARTMENT_NAME = "sixthLevelDepartmentName";
        public static String SEVENLEVEL_DEPARTMENT_ID = "sevenLevelDepartmentId";
        public static String SEVENLEVEL_DEPARTMENT_NAME = "sevenLevelDepartmentName";
        public static String EIGHTLEVEL_DEPARTMENT_ID = "eightLevelDepartmentId";
        public static String EIGHTLEVEL_DEPARTMENT_NAME = "eightLevelDepartmentName";
        public static String NINELEVEL_DEPARTMENT_ID = "nineLevelDepartmentId";
        public static String NINELEVEL_DEPARTMENT_NAME = "nineLevelDepartmentName";
        public static String TENLEVEL_DEPARTMENT_ID = "tenLevelDepartmentId";
        public static String TENLEVEL_DEPARTMENT_NAME = "tenLevelDepartmentName";
        public static String ORGANIZATION_CHART_NODE_TYPE = "organizationChartNodeType";
        public static String SUB_COMPANY_NAME = "subCompanyName";
        public static String DEPARTMENT_CHANGE_ITEM = "departmentChangeItem";
        public static String DEPARTMENT_PATHCHANGE_ITEM = "departmentPathChangeItem";
        public static String POSITION_CHANGE_ITEM = "positionChangeItem";
        public static String STAFF_TYPE_CHANGE_ITEM = "staffTypeChangeItem";
        public static String JOBCATEGORY_CHANGE_ITEM = "jobCategoryChangeItem";
        public static String JOBTITLE_CHANGE_ITEM = "jobTitleChangeItem";
        public static String POSITION_LEVEL_CHANGE_ITEM = "positionLevelChangeItem";
        public static String FLOW_DIRECTION = "flowDirection";
        public static String CHANGE_TYPE = "changeType";
        public static String CHANGE_SUBCLASS = "changeSubclass";
        public static String EFFECTIVE_DATE = "effectiveDate";
        public static String SOURCE = "source";
        public static String MGID = "mgid";
        public static String COMPANY_ID = "companyId";
        public static String ID = "id";
        public static String CREATE_DATE = "createDate";
    }
}
