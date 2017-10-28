package com.foxconn.beacon.salary.fragment.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class OverTimeDecorator implements DayViewDecorator {

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {

    }
}
