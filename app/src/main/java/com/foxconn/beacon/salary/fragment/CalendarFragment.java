package com.foxconn.beacon.salary.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.activity.RecordOvertimeActivity;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.fragment.calendar.HighlightWeekendsDecorator;
import com.foxconn.beacon.salary.fragment.calendar.OvertimeDecorator;
import com.foxconn.beacon.salary.fragment.calendar.TodayDecorator;
import com.foxconn.beacon.salary.model.OverTimeBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public class CalendarFragment extends BaseFragment {
    private static final String TAG = "CalendarFragment";
    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;
    Unbinder unbinder;
    private SQLiteDatabase mLiteDatabase;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initData() {
        mLiteDatabase = LitePal.getDatabase();
        mCalendarView.setSelectedDate(Calendar.getInstance());
        Log.i(TAG, "initData: " + DataSupport.findAll(OverTimeBean.class).size());
        mCalendarView.addDecorators(
                new TodayDecorator(ContextCompat.getColor(getContext(), R.color.app_calendar_today)),
//                加班的装饰
                new OvertimeDecorator(),
//                周末的装饰
                new HighlightWeekendsDecorator()
        );
    }

    @Override
    protected void initEvent() {
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                Intent intent = new Intent(getActivity(), RecordOvertimeActivity.class);
                startActivityForResult(intent, 1);

                List<OverTimeBean> overTimeBeans = DataSupport.where("dateTime=?", String.valueOf(date.getDate().getTime())).find(OverTimeBean.class);
                OverTimeBean overTimeBean;
                if (overTimeBeans.size() > 0) {
                    overTimeBean = overTimeBeans.get(0);
                } else {
                    overTimeBean = new OverTimeBean();
                }
                overTimeBean.setDateTime(date.getDate().getTime());
                overTimeBean.setOvertimeHours(2.0f);
                overTimeBean.setOvertimePay(100.0f);
                overTimeBean.save();
            }
        });
        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
