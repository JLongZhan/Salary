package com.foxconn.beacon.salary.fragment.calendar;



import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.beacon.materialcalendar.CalendarDay;
import com.beacon.materialcalendar.DayViewDecorator;
import com.beacon.materialcalendar.DayViewFacade;

import java.util.List;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */
public class OvertimeDecorator implements DayViewDecorator {
    private static final String TAG = "OvertimeDecorator";
    private CalendarDay mCalendarDay;
    private float[] hours;
    private  List<DayWorkInfo> mMonthOvertimeInfo;

    public OvertimeDecorator(CalendarDay time) {
        mCalendarDay = time;
        setOvertimeHours(mCalendarDay);
        mMonthOvertimeInfo = DBOperatorHelper.getMonthOvertimeInfo(time.getYear(), time.getMonth());

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (day.getMonth() != mCalendarDay.getMonth()) {
            return false;
        }
        for (DayWorkInfo info : mMonthOvertimeInfo) {
            if (info.getDatetime() == day.getDate().getTime()) {
                mMonthOvertimeInfo.remove(info);
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new OvertimeSpan(hours));
    }

    /**
     * 设置加班信息
     *
     * @param day
     */
    public void setOvertimeHours(CalendarDay day) {
        mCalendarDay = day;
        hours = new float[DateUtils.getDaysOfMonth(day.getYear(), day.getMonth() + 1)];
        mMonthOvertimeInfo = DBOperatorHelper.getMonthOvertimeInfo(day.getYear(), day.getMonth());
        for (int i = 0; i < mMonthOvertimeInfo.size(); i++) {
            DayWorkInfo dayWorkInfo = mMonthOvertimeInfo.get(i);
            hours[dayWorkInfo.getDay() - 1] = dayWorkInfo.getOvertimeDuration();
        }
    }
}
