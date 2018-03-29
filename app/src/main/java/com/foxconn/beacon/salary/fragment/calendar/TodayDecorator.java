package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.beacon.materialcalendar.CalendarDay;
import com.beacon.materialcalendar.DayViewDecorator;
import com.beacon.materialcalendar.DayViewFacade;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class TodayDecorator implements DayViewDecorator {
    private static final String TAG = "TodayDecorator";

    public TodayDecorator( ) {
        Drawable highlightDrawable = new ColorDrawable(UIUtils.getColor(R.color.app_calendar_today));
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return DateUtils.getCurrDate().equals(DateUtils.date2String(day.getDate(), DateUtils.LONG_DATE));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new TodaySpan());
    }
}
