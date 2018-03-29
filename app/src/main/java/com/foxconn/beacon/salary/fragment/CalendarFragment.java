package com.foxconn.beacon.salary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.activity.RecordOvertimeActivity;
import com.foxconn.beacon.salary.activity.SalarySettingActivity;
import com.foxconn.beacon.salary.activity.StatisticsSettingActivity;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.fragment.calendar.HighlightWeekendsDecorator;
import com.foxconn.beacon.salary.fragment.calendar.OvertimeDecorator;
import com.foxconn.beacon.salary.fragment.calendar.SelectDayDecorator;
import com.foxconn.beacon.salary.fragment.calendar.TodayDecorator;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.SPUtils;
import com.beacon.materialcalendar.CalendarDay;
import com.beacon.materialcalendar.MaterialCalendarView;
import com.beacon.materialcalendar.OnDateSelectedListener;
import com.beacon.materialcalendar.OnMonthChangedListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.Unbinder;

import static com.foxconn.beacon.salary.constants.MyConstants.REQUEST_CALENDAR;


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
    @BindView(R.id.tv_calendar_overtime_income)
    TextView mTvCalendarOvertimeIncome;
    @BindView(R.id.tv_calendar_overtime_duration)
    TextView mTvCalendarOvertimeDuration;
    @BindView(R.id.iv_calendar_arrow_right)
    ImageView mIvCalendarArrowRight;
    @BindView(R.id.tv_calendar_overtime_detail)
    TextView mTvCalendarOvertimeDetail;
    private CalendarDay mSelectCalendarDay;

    private OvertimeDecorator mOvertimeDecorator;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initData() {
        //        设置日历的标题栏不可见
        mCalendarView.getChildAt(0).setVisibility(View.GONE);
        mSelectCalendarDay = CalendarDay.from(Calendar.getInstance());
        refreshTopOvertimeInfo();
        mOvertimeDecorator = new OvertimeDecorator(mSelectCalendarDay);
        mCalendarView.addDecorators(
                new TodayDecorator(),
//                加班的装饰
                mOvertimeDecorator,
                new SelectDayDecorator(mSelectCalendarDay),
//                周末的装饰
                new HighlightWeekendsDecorator()
        );
        mCalendarView.setCurrentDate(mSelectCalendarDay);
    }

    /**
     * 刷新顶部加班信息
     */
    private void refreshTopOvertimeInfo() {
        mTvCalendarOvertimeIncome.setText("$ " + SalaryOperationHelper.getMonthOvertimeIncome(getContext(),
                mSelectCalendarDay.getYear(),
                mSelectCalendarDay.getMonth()));
        mTvCalendarOvertimeDuration.setText(SalaryOperationHelper.getMonthOvertimeHours(mSelectCalendarDay.getYear(),
                mSelectCalendarDay.getMonth()) + " h");
        mMainActivity.setCalendarPageTitle(mSelectCalendarDay.getYear(), mSelectCalendarDay.getMonth());
    }


    @Override
    protected void initEvent() {
        mTvCalendarOvertimeDetail.setOnClickListener(this);
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                判断是否设置了基本工资
                if (SPUtils.getFloat(mMainActivity, MyConstants.BASIC_SALARY, 0.0f) > 0.0f) {
                    dumpToRecordOvertime(date);
                } else {
                    startActivity(new Intent(mMainActivity, SalarySettingActivity.class));
                }
            }
        });
        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mSelectCalendarDay = date;
                mOvertimeDecorator.setOvertimeHours(date);
                mCalendarView.invalidateDecorators();
                refreshTopOvertimeInfo();
            }
        });
    }

    public CalendarDay getSelectCalendarDay() {
        return mSelectCalendarDay;
    }

    @Override
    protected void processClickListener(View view) {
        switch (view.getId()) {
//             进入加班详情页面
            case R.id.tv_calendar_overtime_detail:
                Intent intent = new Intent(getActivity(), StatisticsSettingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "加班明细");
                bundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, -1);
                bundle.putInt(MyConstants.CURRENT_YEAR, mSelectCalendarDay.getYear());
                bundle.putInt(MyConstants.CURRENT_MONTH, mSelectCalendarDay.getMonth());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 进入记加班界面
     *
     * @param date
     */
    private void dumpToRecordOvertime(@NonNull CalendarDay date) {
        Intent intent = new Intent(getActivity(), RecordOvertimeActivity.class);
        intent.putExtra(MyConstants.IS_FROM_CALENDAR, true);
        intent.putExtra(MyConstants.SELECT_DATE, date.getDate().getTime());
        intent.putExtra(MyConstants.SELECT_DAY_INFO, DBOperatorHelper.getDayOvertimeInfo(date.getDate().getTime()));
        startActivityForResult(intent, REQUEST_CALENDAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CALENDAR:
                mOvertimeDecorator.setOvertimeHours(mSelectCalendarDay);
                mCalendarView.invalidateDecorators();
                break;
            default:
                break;
        }
    }
}
