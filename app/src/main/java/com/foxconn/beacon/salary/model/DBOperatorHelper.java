package com.foxconn.beacon.salary.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.beacon.materialcalendar.CalendarDay;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/11/3 0003.
 * @describe:
 */

public class DBOperatorHelper {

    private static MonthSetting mMonthSetting;

    /**
     * 获取某一天的出勤记录
     *
     * @param datetime
     * @return
     */
    public static DayWorkInfo getDayOvertimeInfo(@NonNull long datetime) {
        List<DayWorkInfo> dayWorkInfos = DataSupport.where("datetime=?", datetime + "").find(DayWorkInfo.class);
        Log.i("sss", "getDayOvertimeInfo: " + CalendarDay.from(new Date(datetime)).getDay());
        if (dayWorkInfos.size() > 0) {
            return dayWorkInfos.get(0);
        }
        return null;
    }

    /**
     * 返回最近的出勤记录
     *
     * @return
     */
    public static List<DayWorkInfo> getAllOvertimeInfo() {
        List<DayWorkInfo> datetime = DataSupport.order("datetime").find(DayWorkInfo.class);
        Collections.reverse(datetime);
        return datetime;
    }

    /**
     * 获取调休信息
     *
     * @param year
     * @param month
     * @return 一个月的调休小时数目
     */
    public static float getAdjustHours(int year, int month) {
        MonthSetting setting = DBOperatorHelper.getMonthSetting(year, month);
//        是否自定义调休
        if (setting.isCustomAdjustHours()) {
            return setting.getCustomAdjustHours();
        } else {
            return getMonthLeaveInfo(year, month, 4);
        }
    }

    /**
     * y一个月的请假信息
     *
     * @param year
     * @param month
     * @param type
     * @return
     */
    public static float getMonthLeaveInfo(int year, int month, int type) {
        float hours = .0f;
        List<DayWorkInfo> infos = getMonthOvertimeInfo(year, month);
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getLeaveType() == type) {
                hours += infos.get(i).getLeaveDuration();
            }
        }
        return hours;
    }

    /**
     * 获取加班类型
     *
     * @return
     */
    public static String getOvertimeType(float type) {
        if (type == 1.5f) {
            return "(工作日1.5倍)";
        } else if (type == 2.0f) {
            return "休息日2.0倍";
        } else {
            return "节假日3.0倍";
        }
    }

    /**
     * 出班类别
     * 0：默认 1：白班 2：中班 3：晚班 4:休息
     *
     * @param type
     * @return
     */
    public static String getWorkShiftType(int type) {
        String leave;
        switch (type) {
            case 0:
                leave = "默认";
                break;
            case 1:
                leave = "白班";
                break;
            case 2:
                leave = "中班";
                break;
            case 3:
                leave = "晚班";
                break;
            default:
                leave = "休息";
                break;
        }
        return leave;
    }

    /**
     * 0：无请假 1：带薪休假 2：事假 3：病假 4:调休 5：其他
     *
     * @param type
     * @return
     */
    public static String getLeaveType(int type) {
        String leave;
        switch (type) {
            case 0:
                leave = "无请假";
                break;
            case 1:
                leave = "带薪休假";
                break;
            case 2:
                leave = "事假";
                break;
            case 3:
                leave = "病假";
                break;
            case 4:
                leave = "调休";
                break;
            default:
                leave = "其他";
                break;
        }
        return leave;
    }

    /**
     * 获取一个月的病假小时
     *
     * @param year
     * @param month
     * @return
     */
    public static float getMonthSickLeaveHours(int year, int month) {
        MonthSetting monthSetting = getMonthSetting(year, month);
        if (monthSetting.isCustomSickLeaveHours()) {
            return monthSetting.getCustomSickLeaveHours();
        } else {
            return getMonthLeaveInfo(year, month, 3);
        }
    }

    /**
     * 获取一个月的事假小时
     *
     * @param year
     * @param month
     * @return
     */
    public static float getMonthThingsLeaveHours(int year, int month) {
        MonthSetting monthSetting = getMonthSetting(year, month);
        if (monthSetting.isCustomThingsLeaveHours()) {
            return monthSetting.getCustomThingsLeaveHours();
        } else {
            return getMonthLeaveInfo(year, month, 2);
        }
    }

    /**
     * 获取一个月各种班别天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthWorkTypeDays(int year, int month, int type) {
        List<DayWorkInfo> monthOvertimeInfo = getMonthOvertimeInfo(year, month);
        int days = 0;
        for (int i = 0; i < monthOvertimeInfo.size(); i++) {
            DayWorkInfo dayWorkInfo = monthOvertimeInfo.get(i);
            int workType = dayWorkInfo.getWorkType();
            if (workType == type) {
                days += 1;
            }
        }
        return days;
    }

    /**
     * 获取一个月的加班信息
     *
     * @param year  获取信息的年份
     * @param month 获取信息的月份
     * @return 工作信息的集合
     */
    public static List<DayWorkInfo> getMonthOvertimeInfo(int year, int month) {
        List<DayWorkInfo> dayOvertimeBeen = DataSupport.where("year=? and month=?",
                String.valueOf(year), String.valueOf(month)).order("datetime desc").find(DayWorkInfo.class);
        return dayOvertimeBeen;
    }

    /**
     * @param year
     * @param month
     * @return 某个月的设置
     */
    public static MonthSetting getMonthSetting(int year, int month) {
        if (mMonthSetting != null && mMonthSetting.getYear() == year && mMonthSetting.getMonth() == month) {
            return mMonthSetting;
        }
        List<MonthSetting> monthSettings = DataSupport.where("year = ? and month = ?", String.valueOf(year), String.valueOf(month)).find(MonthSetting.class);
        if (monthSettings.size() != 0) {
            mMonthSetting = monthSettings.get(0);
        } else {
            MonthSetting monthSetting = new MonthSetting();
            monthSetting.setYear(year);
            monthSetting.setMonth(month);
            monthSetting.save();
            mMonthSetting = monthSetting;
        }
        return mMonthSetting;
    }
}
