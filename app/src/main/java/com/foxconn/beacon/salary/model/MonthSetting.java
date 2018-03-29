package com.foxconn.beacon.salary.model;

import org.litepal.crud.DataSupport;

/**
 * @author: F1331886
 * @date: 2017/11/6 0006.
 * @describe:
 */

public class MonthSetting extends DataSupport {
    private int id;
    private int year;
    private int month;
    private boolean isCustomBasicSalary;
    private float customBasicSalary;
    private boolean isCustomAdjustHours;
    private float customAdjustHours;

    /**
     * 调休抵扣类型
     */
    private int adjustOffsetType;
    private String subsidyItemChecked;
    private String deductItemChecked;
    /**
     * 白班补贴
     */
    private float dayShiftSubsidy;
    /**
     * 中班补贴
     */
    private float middleShiftSubsidy;
    /**
     * 晚班补贴
     */
    private float nightShiftSubsidy;
    /**
     * 全勤奖
     */
    private float attendanceBonus;
    /**
     * 交通补贴
     */
    private float trafficSubsidy;

    /**
     * 伙食补贴
     */
    private float foodSubsidy;
    /**
     * 生活补贴
     */
    private float liveSubsidy;
    /**
     * 岗位补贴
     */
    private float jobSubsidy;
    /**
     * 高温补贴
     */
    private float hyperthermiaSubsidy;
    /**
     * 环境补贴
     */
    private float environmentSubsidy;
    /**
     * 绩效补贴
     */
    private float performanceSubsidy;
    /**
     * 其他补贴
     */
    private float otherSubsidy;
    /**
     * 事假扣除百分比
     */
    private float thingsLeavePercent;
    /**
     * 是否自定义事假小时数目
     */
    private boolean isCustomThingsLeaveHours;
    /**
     * 自定义的事假小时数目
     */
    private float customThingsLeaveHours;
    /**
     * 是否自定义病假小时
     */
    private boolean isCustomSickLeaveHours;
    /**
     * 病假
     */
    private float sickLeavePercent;
    /**
     * 病假小时数目
     */
    private float customSickLeaveHours;
    /**
     * 食堂消费
     */
    private float messConsume;
    /**
     * 水电费
     */
    private float mUtilities;
    /**
     * 住宿费
     */
    private float quarterage;
    /**
     * 其他扣除
     */
    private float otherDeduct;
    /**
     * 社保类型
     */
    private int socialSecurityType;
    /**
     * 社保数值
     */
    private float socialSecurityValue;
    /**
     * 公积金类型
     */
    private int accumulationFundType;
    /**
     * 公积金数值
     */
    private float accumulationFundValue;
    /**
     * 是否自定义所得税
     */
    private boolean isCustomIncomeTax;
    /**
     * 自定义的个人所得税
     */
    private float customIncomeTax;


    public boolean isCustomThingsLeaveHours() {
        return isCustomThingsLeaveHours;
    }

    public void setCustomThingsLeaveHours(boolean customThingsLeaveHours) {
        isCustomThingsLeaveHours = customThingsLeaveHours;
    }

    public float getCustomThingsLeaveHours() {
        return customThingsLeaveHours;
    }

    public void setCustomThingsLeaveHours(float customThingsLeaveHours) {
        this.customThingsLeaveHours = customThingsLeaveHours;
    }

    public boolean isCustomSickLeaveHours() {
        return isCustomSickLeaveHours;
    }

    public void setCustomSickLeaveHours(boolean customSickLeaveHours) {
        isCustomSickLeaveHours = customSickLeaveHours;
    }

    public float getCustomSickLeaveHours() {
        return customSickLeaveHours;
    }

    public void setCustomSickLeaveHours(float customSickLeaveHours) {
        this.customSickLeaveHours = customSickLeaveHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isCustomBasicSalary() {
        return isCustomBasicSalary;
    }

    public void setCustomBasicSalary(boolean customBasicSalary) {
        isCustomBasicSalary = customBasicSalary;
    }

    public String getSubsidyItemChecked() {
        return subsidyItemChecked;
    }

    public void setSubsidyItemChecked(String subsidyItemChecked) {
        this.subsidyItemChecked = subsidyItemChecked;
    }

    public String getDeductItemChecked() {
        return deductItemChecked;
    }

    public void setDeductItemChecked(String deductItemChecked) {
        this.deductItemChecked = deductItemChecked;
    }

    public float getCustomBasicSalary() {
        return customBasicSalary;
    }

    public void setCustomBasicSalary(float customBasicSalary) {
        this.customBasicSalary = customBasicSalary;
    }

    public boolean isCustomAdjustHours() {
        return isCustomAdjustHours;
    }

    public void setCustomAdjustHours(boolean customAdjustHours) {
        isCustomAdjustHours = customAdjustHours;
    }

    public float getCustomAdjustHours() {
        return customAdjustHours;
    }

    public void setCustomAdjustHours(float customAdjustHours) {
        this.customAdjustHours = customAdjustHours;
    }

    public int getAdjustOffsetType() {
        return adjustOffsetType;
    }

    public void setAdjustOffsetType(int adjustOffsetType) {
        this.adjustOffsetType = adjustOffsetType;
    }

    public float getDayShiftSubsidy() {
        return dayShiftSubsidy;
    }

    public void setDayShiftSubsidy(float dayShiftSubsidy) {
        this.dayShiftSubsidy = dayShiftSubsidy;
    }

    public float getMiddleShiftSubsidy() {
        return middleShiftSubsidy;
    }

    public void setMiddleShiftSubsidy(float middleShiftSubsidy) {
        this.middleShiftSubsidy = middleShiftSubsidy;
    }

    public float getNightShiftSubsidy() {
        return nightShiftSubsidy;
    }

    public void setNightShiftSubsidy(float nightShiftSubsidy) {
        this.nightShiftSubsidy = nightShiftSubsidy;
    }

    public float getAttendanceBonus() {
        return attendanceBonus;
    }

    public void setAttendanceBonus(float attendanceBonus) {
        this.attendanceBonus = attendanceBonus;
    }

    public float getTrafficSubsidy() {
        return trafficSubsidy;
    }

    public void setTrafficSubsidy(float trafficSubsidy) {
        this.trafficSubsidy = trafficSubsidy;
    }

    public float getFoodSubsidy() {
        return foodSubsidy;
    }

    public void setFoodSubsidy(float foodSubsidy) {
        this.foodSubsidy = foodSubsidy;
    }

    public float getLiveSubsidy() {
        return liveSubsidy;
    }

    public void setLiveSubsidy(float liveSubsidy) {
        this.liveSubsidy = liveSubsidy;
    }

    public float getJobSubsidy() {
        return jobSubsidy;
    }

    public void setJobSubsidy(float jobSubsidy) {
        this.jobSubsidy = jobSubsidy;
    }

    public float getHyperthermiaSubsidy() {
        return hyperthermiaSubsidy;
    }

    public void setHyperthermiaSubsidy(float hyperthermiaSubsidy) {
        this.hyperthermiaSubsidy = hyperthermiaSubsidy;
    }

    public float getEnvironmentSubsidy() {
        return environmentSubsidy;
    }

    public void setEnvironmentSubsidy(float environmentSubsidy) {
        this.environmentSubsidy = environmentSubsidy;
    }

    public float getPerformanceSubsidy() {
        return performanceSubsidy;
    }

    public void setPerformanceSubsidy(float performanceSubsidy) {
        this.performanceSubsidy = performanceSubsidy;
    }

    public float getOtherSubsidy() {
        return otherSubsidy;
    }

    public void setOtherSubsidy(float otherSubsidy) {
        this.otherSubsidy = otherSubsidy;
    }

    public float getThingsLeavePercent() {
        return thingsLeavePercent;
    }

    public void setThingsLeavePercent(float thingsLeavePercent) {
        this.thingsLeavePercent = thingsLeavePercent;
    }

    public float getSickLeavePercent() {
        return sickLeavePercent;
    }

    public void setSickLeavePercent(float sickLeavePercent) {
        this.sickLeavePercent = sickLeavePercent;
    }

    public float getMessConsume() {
        return messConsume;
    }

    public void setMessConsume(float messConsume) {
        this.messConsume = messConsume;
    }

    public float getUtilities() {
        return mUtilities;
    }

    public void setUtilities(float utilities) {
        mUtilities = utilities;
    }

    public float getQuarterage() {
        return quarterage;
    }

    public void setQuarterage(float quarterage) {
        this.quarterage = quarterage;
    }

    public float getOtherDeduct() {
        return otherDeduct;
    }

    public void setOtherDeduct(float otherDeduct) {
        this.otherDeduct = otherDeduct;
    }

    public int getSocialSecurityType() {
        return socialSecurityType;
    }

    public void setSocialSecurityType(int socialSecurityType) {
        this.socialSecurityType = socialSecurityType;
    }

    public float getSocialSecurityValue() {
        return socialSecurityValue;
    }

    public void setSocialSecurityValue(float socialSecurityValue) {
        this.socialSecurityValue = socialSecurityValue;
    }

    public int getAccumulationFundType() {
        return accumulationFundType;
    }

    public void setAccumulationFundType(int accumulationFundType) {
        this.accumulationFundType = accumulationFundType;
    }

    public float getAccumulationFundValue() {
        return accumulationFundValue;
    }

    public void setAccumulationFundValue(float accumulationFundValue) {
        this.accumulationFundValue = accumulationFundValue;
    }

    public boolean isCustomIncomeTax() {
        return isCustomIncomeTax;
    }

    public void setCustomIncomeTax(boolean customIncomeTax) {
        isCustomIncomeTax = customIncomeTax;
    }

    public float getCustomIncomeTax() {
        return customIncomeTax;
    }

    public void setCustomIncomeTax(float customIncomeTax) {
        this.customIncomeTax = customIncomeTax;
    }
}
