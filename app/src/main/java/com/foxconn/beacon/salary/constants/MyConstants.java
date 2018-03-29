package com.foxconn.beacon.salary.constants;

/**
 * @author: F1331886
 * @date: 2017/11/1 0001.
 * @describe:
 */

public class MyConstants {
    public static final String SELECT_DATE = "select_date";
    public static final String IS_FROM_CALENDAR = "is_from_calendar";
    public static final String GOTOCALENDAR = "go_to_calendar";
    /**
     * 结果码
     */
    public static final int RESULT_RECORD_OVERTIME_TO_CALENDAR = 1000;
    public static final int RESULT_STATISTICS_SETTING = 1001;
    public static final int RESULT_STATISTICS_SETTING_DEDUCT = 1002;
    /**
     * 请求码
     */
    public static final int REQUEST_CALENDAR = 100;
    public static final int REQUEST_STATISTICS_SETTING = 101;


    public static final String CURRENT_YEAR = "current_year";
    public static final String CURRENT_MONTH = "current_month";
    /**
     * 工作日加班倍率
     */
    public static final float WORK_DATE_OVERTIME_SALARY = 1.5f;
    public static final float REST_DATE_OVERTIME_SALARY = 2.0f;
    public static final float HOLIDAY_DATE_OVERTIME_SALARY = 3.0f;


    /**
     * 基本工资
     */
    public static final String BASIC_SALARY = "basic_salary";
    /**
     * 社保
     */
    public static final String SOCIAL_SECURITY = "social_security";
    /**
     * 公积金
     */
    public static final String ACCUMULATION_FUND = "accumulation_fund";
    /**
     * 个人所得税起征点
     */
    public static final int INCOME_TAX_BEGIN_NUMBER = 3500;
    public static final String STATISTICS_SETTING_TITLE = "statistics_setting_title";
    /**
     * 是扣款项目还是补贴项目
     */
    public static final String IS_DEDUCT = "is_deduct";

    public static String STATISTICS_SETTING_LAYOUT = "statistics_setting_layout";
    /**
     * 是否自动获取基本工资
     */
    public static String IS_AUTO_GET_BASIC_SALARY = "is_auto_get_salary";
    /**
     * 扣款项目、补贴项目中选中的Item
     */
    public static String CHECKED_ITEM_DEDUCT = "checked_item_deduct";

    public static String SELECT_DAY_INFO = "select_day_info";
}
