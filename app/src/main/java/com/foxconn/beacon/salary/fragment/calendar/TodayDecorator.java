package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.base.BaseApplication;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class TodayDecorator implements DayViewDecorator {
    private static final String TAG = "TodayDecorator";
    int mColor;

    public TodayDecorator(int color) {
        mColor = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.getDate().equals(Calendar.getInstance().getTime());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(mColor));
    }
}
