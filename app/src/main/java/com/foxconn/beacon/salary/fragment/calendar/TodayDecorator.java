package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.base.BaseApplication;
import com.foxconn.beacon.salary.utils.DateUtils;
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
    private Drawable highlightDrawable;

    public TodayDecorator(int color) {
        highlightDrawable = new ColorDrawable(color);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Date date = new Date();
        String dateStr = DateUtils.date2String(date, "yyyy-MM-dd");
        Date parse = DateUtils.string2Date(dateStr, "yyyy-MM-dd");
        return day.getDate().equals(parse);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(highlightDrawable);
    }
}
