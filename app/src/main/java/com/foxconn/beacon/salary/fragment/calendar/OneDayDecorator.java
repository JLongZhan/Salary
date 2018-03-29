package com.foxconn.beacon.salary.fragment.calendar;

import android.graphics.Typeface;
import android.text.style.StyleSpan;

import com.beacon.materialcalendar.CalendarDay;
import com.beacon.materialcalendar.DayViewDecorator;
import com.beacon.materialcalendar.DayViewFacade;
import com.beacon.materialcalendar.MaterialCalendarView;

import java.util.Date;

/**
 * Decorate a day by making the text big and bold
 */
public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public OneDayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD_ITALIC));
//        view.addSpan(new RelativeSizeSpan(1.4f));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
