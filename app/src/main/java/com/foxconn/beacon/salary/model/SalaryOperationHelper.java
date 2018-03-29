package com.foxconn.beacon.salary.model;

import android.content.Context;

import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.utils.SPUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/11/2 0002.
 * @describe: 数据库的操作类
 */

public class SalaryOperationHelper {
    private static final String TAG = "SalaryOperationHelper";
    private static DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

    private SalaryOperationHelper() {
    }

    /**
     * 获取实发工资
     * 总收入-总支出
     *
     * @param ctx
     * @param year
     * @param month
     * @return
     */
    public static float getFSalary(Context ctx, int year, int month) {
        float v = getMonthAllIncome(ctx, year, month) - getMonthAllDeduct(ctx, year, month);
        return Float.valueOf(mDecimalFormat.format(v));
    }

    /**
     * 所有扣除的费用
     * 请假+社保+公积金+所得税
     *
     * @return
     */
    public static float getMonthAllDeduct(Context ctx, int year, int month) {
        return getOtherExpenditure(ctx, year, month) + getMonthAgencyDeduct(ctx, year, month);
    }

    /**
     * 代缴费的费用
     * 社保+公积金+所得税
     *
     * @param ctx
     * @param year
     * @param month
     * @return
     */
    public static float getMonthAgencyDeduct(Context ctx, int year, int month) {
        float v = getAccumulationFund(ctx, year, month) + getSocialSecurity(ctx, year, month) +
                getIncomeTax(ctx, year, month);
        return Float.parseFloat(mDecimalFormat.format(v));
    }

    /**
     * 获取个人所得税
     *
     * @param ctx
     * @param year
     * @param month
     * @return
     */
    public static float getIncomeTax(Context ctx, int year, int month) {
        double personIncomeTax = getPersonIncomeTax(ctx, year, month);
        return Float.valueOf(mDecimalFormat.format(personIncomeTax));
    }

    /**
     * 计算个人所得税
     * 缴税=全月应纳税所得额*税率－速算扣除数
     * 全月应纳税所得额=(应发工资－四金)－3500
     * 实发工资=应发工资－四金－缴税
     *
     * @return
     */
    public static double getPersonIncomeTax(Context context, int year, int month) {
//        全月应纳税所得额
        float v = getMonthAllIncome(context, year, month) -
                getOtherExpenditure(context, year, month) -
                getSocialSecurity(context, year, month) -
                getAccumulationFund(context, year, month) -
                MyConstants.INCOME_TAX_BEGIN_NUMBER;
        if (v < 0) {
            return 0.00;
        } else if (v < 1455) {
            return v * 0.03;
        } else if (v < 4155) {
            return v * 0.1 - 105;
        } else if (v < 7755) {
            return v * 0.2 - 555;
        } else if (v < 27255) {
            return v * 0.25 - 1005;
        } else if (v < 41255) {
            return v * 0.3 - 2755;
        } else if (v < 57505) {
            return v * 0.35 - 5505;
        } else {
            return v * 0.45 - 13505;
        }


    }

    /**
     * 获取社保金额
     *
     * @return
     */
    public static float getSocialSecurity(Context context, int year, int month) {
        MonthSetting setting = DBOperatorHelper.getMonthSetting(year, month);
//        百分比
        if (setting.getSocialSecurityType() == 0) {
            return setting.getSocialSecurityValue() * getBasicSalary(context, year, month) / 100;
        } else {
            return setting.getSocialSecurityValue();
        }
    }

    /**
     * 获取公积金
     *
     * @return
     */
    public static float getAccumulationFund(Context context, int year, int month) {
        MonthSetting setting = DBOperatorHelper.getMonthSetting(year, month);
//        百分比
        if (setting.getAccumulationFundType() == 0) {
            return Float.parseFloat(mDecimalFormat.format(setting.getAccumulationFundValue() / 100 * getBasicSalary(context, year, month)));
        } else {
            return setting.getAccumulationFundValue();
        }
    }

    /**
     * 获取月份加班总收入
     *
     * @param year
     * @param month
     * @return
     */
    public static float getMonthOvertimeIncome(Context ctx, int year, int month) {
        float income = 0.0f;
        List<DayWorkInfo> dayWorkInfos = DBOperatorHelper.getMonthOvertimeInfo(year, month);
        for (int i = 0; i < dayWorkInfos.size(); i++) {
            DayWorkInfo dayWorkTime = dayWorkInfos.get(i);
            income += getDayOvertimeSalary(ctx, dayWorkTime.getOvertimeDuration(),
                    dayWorkTime.getOvertimeType(), year, month);
        }
        return Float.valueOf(mDecimalFormat.format(income));
    }

    /**
     * 获取月份加班总时数
     *
     * @param year
     * @param month
     * @return
     */
    public static float getMonthOvertimeHours(int year, int month) {
        float hours = 0.0f;
        List<DayWorkInfo> dayWorkInfos = DBOperatorHelper.getMonthOvertimeInfo(year, month);
        for (int i = 0; i < dayWorkInfos.size(); i++) {
            DayWorkInfo dayOvertime = dayWorkInfos.get(i);
            hours += dayOvertime.getOvertimeDuration();
        }
        return hours;
    }

    /**
     * 获取当月总收入
     * 总收入= 基本工资+加班收入+其他收入
     *
     * @param context
     * @param year
     * @param month
     * @return
     */
    public static float getMonthAllIncome(Context context, int year, int month) {
        return getMonthOvertimeIncome(context, year, month) +
                getBasicSalary(context, year, month) +
                getOtherIncome(year, month);
    }

    /**
     * 获取其他收入、补贴
     *
     * @param year
     * @param month
     * @return
     */
    public static float getOtherIncome(int year, int month) {
        MonthSetting setting = DBOperatorHelper.getMonthSetting(year, month);
        return setting.getDayShiftSubsidy() * (DBOperatorHelper.getMonthWorkTypeDays(year, month, 0) + DBOperatorHelper.getMonthWorkTypeDays(year, month, 1)) +
                setting.getMiddleShiftSubsidy() * DBOperatorHelper.getMonthWorkTypeDays(year, month, 2) +
                setting.getNightShiftSubsidy() * DBOperatorHelper.getMonthWorkTypeDays(year, month, 3) +
                setting.getAttendanceBonus() + setting.getTrafficSubsidy() + setting.getFoodSubsidy() +
                setting.getLiveSubsidy() + setting.getJobSubsidy() + setting.getHyperthermiaSubsidy() +
                setting.getEnvironmentSubsidy() + setting.getPerformanceSubsidy() + setting.getOtherSubsidy();
    }

    /**
     * 获取其他支出、请假、调休
     *
     * @param year
     * @param month
     * @return
     */
    public static float getOtherExpenditure(Context ctx, int year, int month) {
        MonthSetting setting = DBOperatorHelper.getMonthSetting(year, month);
        return getAdjustCost(ctx, year, month) +
                getThingLeaveCost(ctx, year, month) +
                getSickLeaveCost(ctx, year, month) +
                setting.getMessConsume() + setting.getUtilities() +
                setting.getQuarterage() + setting.getOtherDeduct();
    }

    /**
     * 事假每小时支出
     *
     * @param year
     * @param month
     * @return
     */
    public static float getThingLeaveHour(Context ctx, int year, int month) {
        float eachHourSalary = getEachHourSalary(ctx, year, month);
        MonthSetting monthSetting = DBOperatorHelper.getMonthSetting(year, month);
        float thingsLeavePercent = monthSetting.getThingsLeavePercent();
        String format = mDecimalFormat.format(eachHourSalary * thingsLeavePercent / 100);
        return Float.parseFloat(format);
    }

    /**
     * 获取事假支出
     *
     * @return
     */
    public static float getThingLeaveCost(Context ctx, int year, int month) {
        float adjustHours = DBOperatorHelper.getMonthThingsLeaveHours(year, month);
        float adjustHourCost = getThingLeaveHour(ctx, year, month);
        return Float.parseFloat(mDecimalFormat.format(adjustHourCost * adjustHours));
    }

    /**
     * 病假每小时支出
     *
     * @param year
     * @param month
     * @return
     */
    public static float getSickLeaveHour(Context ctx, int year, int month) {
        float eachHourSalary = getEachHourSalary(ctx, year, month);
        MonthSetting monthSetting = DBOperatorHelper.getMonthSetting(year, month);
        float sickLeavePercent = monthSetting.getSickLeavePercent();
        String format = mDecimalFormat.format(eachHourSalary * sickLeavePercent / 100);
        return Float.parseFloat(format);
    }

    /**
     * 获取病假支出
     *
     * @return
     */
    public static float getSickLeaveCost(Context ctx, int year, int month) {
        float adjustHours = DBOperatorHelper.getMonthSickLeaveHours(year, month);
        float adjustHourCost = getSickLeaveHour(ctx, year, month);
        return Float.parseFloat(mDecimalFormat.format(adjustHourCost * adjustHours));
    }

    /**
     * 获取调休支出
     *
     * @return
     */
    public static float getAdjustCost(Context ctx, int year, int month) {
        float adjustHours = DBOperatorHelper.getAdjustHours(year, month);
        float adjustHourCost = getAdjustHourCost(ctx, year, month);
        return Float.parseFloat(mDecimalFormat.format(adjustHourCost * adjustHours));
    }

    /**
     * 调休每小时支出
     *
     * @param year
     * @param month
     * @return
     */
    public static float getAdjustHourCost(Context ctx, int year, int month) {
        float cost = 0.0f;
        MonthSetting setting = DBOperatorHelper.getMonthSetting(year, month);
        switch (setting.getAdjustOffsetType()) {
            case 0:
                cost = 0.0f;
                break;
            case 1:
                cost = getEachHourSalary(ctx, year, month);
                break;
            case 2:
                cost = getWorkDateOvertimeSalary(ctx, year, month);
                break;
            case 3:
                cost = getRestDateOvertimeSalary(ctx, year, month);
                break;
            default:
                break;
        }
        return cost;
    }


    /**
     * 获取一天的加班工资
     *
     * @param hours        加班时数
     * @param overtimeType 加班类型
     * @return
     */
    public static float getDayOvertimeSalary(Context context, float hours, float overtimeType, int year, int month) {
        return Float.valueOf(mDecimalFormat.format(hours *
                overtimeType * getEachHourSalary(context, year, month)));
    }


    /**
     * 获取工作日加班工资
     *
     * @return 每小时的工资
     */
    public static float getWorkDateOvertimeSalary(Context context, int year, int month) {
        return Float.valueOf(mDecimalFormat.format(getEachHourSalary(context, year, month) * MyConstants.WORK_DATE_OVERTIME_SALARY));
    }

    /**
     * 获取休息日加班工资
     *
     * @return 每小时的工资
     */
    public static float getRestDateOvertimeSalary(Context context, int year, int month) {
        return Float.valueOf(mDecimalFormat.format(getEachHourSalary(context, year, month) * MyConstants.REST_DATE_OVERTIME_SALARY));
    }


    /**
     * 获取节假日加班工资
     *
     * @return 每小时的工资
     */
    public static float getHolidayDateOvertimeSalary(Context context, int year, int month) {
        return Float.valueOf(mDecimalFormat.format(getEachHourSalary(context, year, month) *
                MyConstants.HOLIDAY_DATE_OVERTIME_SALARY));
    }

    /**
     * 获取每小时的基本工资
     *
     * @param context
     * @return
     */
    public static float getEachHourSalary(Context context, int year, int month) {
        return Float.valueOf(mDecimalFormat.format(getEachDaySalary(context, year, month) / 8));
    }

    /**
     * 获取每日的基本工资
     *
     * @param context
     * @return
     */
    public static float getEachDaySalary(Context context, int year, int month) {
        float basicSalary = getBasicSalary(context, year, month);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return Float.valueOf(decimalFormat.format(basicSalary / 21.75));
    }

    /**
     * 获取基本工资
     *
     * @param ctx
     * @param year
     * @param month
     * @return
     */
    public static float getBasicSalary(Context ctx, int year, int month) {
        MonthSetting monthSetting = DBOperatorHelper.getMonthSetting(year, month);
        if (monthSetting.isCustomBasicSalary()) {
            return monthSetting.getCustomBasicSalary();
        } else {
            return SPUtils.getFloat(ctx, MyConstants.BASIC_SALARY, 0.0f);
        }
    }
}
