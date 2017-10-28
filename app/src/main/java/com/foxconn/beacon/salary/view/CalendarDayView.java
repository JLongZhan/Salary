package com.foxconn.beacon.salary.view;

import android.support.v4.content.ContextCompat;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseApplication;
import com.foxconn.beacon.salary.fragment.calendar.MySpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class CalendarDayView implements DayViewDecorator {
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new MySpan());
    }
}
