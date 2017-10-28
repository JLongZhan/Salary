package com.foxconn.beacon.salary.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.fragment.calendar.HighlightWeekendsDecorator;
import com.foxconn.beacon.salary.fragment.calendar.MySelectorDecorator;
import com.foxconn.beacon.salary.fragment.calendar.OneDayDecorator;
import com.foxconn.beacon.salary.fragment.calendar.TodayDecorator;
import com.foxconn.beacon.salary.view.CalendarDayView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public class CalendarFragment extends BaseFragment {
    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;
    Unbinder unbinder;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initData() {
        mCalendarView.setSelectedDate(Calendar.getInstance());
        mCalendarView.addDecorators(
//                今日的装饰
                new TodayDecorator(ContextCompat.getColor(getContext(), R.color.app_base_color)),
//                周末的装饰
                new HighlightWeekendsDecorator()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
