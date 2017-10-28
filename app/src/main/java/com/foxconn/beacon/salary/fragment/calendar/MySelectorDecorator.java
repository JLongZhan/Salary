package com.foxconn.beacon.salary.fragment.calendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.foxconn.beacon.salary.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {


    public MySelectorDecorator(Activity context) {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new SelectDaySpan());
    }
}
