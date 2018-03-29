package com.foxconn.beacon.salary.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe: 每个月份的加班时数统计
 */

public class DayWorkInfo extends DataSupport implements Serializable {
    private int id;
    private int year;
    private int month;
    private int day;
    private long datetime;
    private int workType;
    /**
     * 每日的备注信息
     */
    private String remark;
    /**
     * 请假类型  0：无请假 1：带薪休假 2：事假 3：病假 4:调休 5：其他
     */
    private int leaveType;
    /**
     * 请假时长
     */
    private float leaveDuration;
    /**
     * 加班时间
     */
    private float overtimeDuration;
    /**
     * 加班类型  1.5,2.0,3.0
     */
    private float overtimeType;

    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }

    public float getLeaveDuration() {
        return leaveDuration;
    }

    public void setLeaveDuration(float leaveDuration) {
        this.leaveDuration = leaveDuration;
    }

    public float getOvertimeDuration() {
        return overtimeDuration;
    }

    public void setOvertimeDuration(float overtimeDuration) {
        this.overtimeDuration = overtimeDuration;
    }

    public float getOvertimeType() {
        return overtimeType;
    }

    public void setOvertimeType(float overtimeType) {
        this.overtimeType = overtimeType;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public int getWorkType() {
        return workType;
    }


    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 出勤时间
     */
    private float workTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }


    public float getWorkTime() {
        return workTime;
    }

    public void setWorkTime(float workTime) {
        this.workTime = workTime;
    }
}
