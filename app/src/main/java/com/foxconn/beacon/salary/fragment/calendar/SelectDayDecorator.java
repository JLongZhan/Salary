package com.foxconn.beacon.salary.fragment.calendar;

import com.foxconn.beacon.salary.utils.DateUtils;
import com.beacon.materialcalendar.CalendarDay;
import com.beacon.materialcalendar.DayViewDecorator;
import com.beacon.materialcalendar.DayViewFacade;

import java.util.Date;

/**
 * @author F1331886
 */
public class SelectDayDecorator implements DayViewDecorator {

    private int color;
    private CalendarDay mCalendarDay;

    public SelectDayDecorator(CalendarDay calendarDay) {
        mCalendarDay = calendarDay;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mCalendarDay != null && DateUtils.date2String(mCalendarDay.getDate(), DateUtils.LONG_DATE).equals(DateUtils.date2String(day.getDate(), DateUtils.LONG_DATE));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new SelectDaySpan());
    }


    public void setDate(Date date) {
        this.mCalendarDay = CalendarDay.from(date);
    }
}
