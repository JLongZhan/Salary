package com.foxconn.beacon.salary.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.activity.RecordOvertimeActivity;
import com.foxconn.beacon.salary.activity.SalarySettingActivity;
import com.foxconn.beacon.salary.adapter.OverTimeCheckingInAdapter;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.ActivityUtils;
import com.foxconn.beacon.salary.utils.SPUtils;
import com.beacon.materialcalendar.CalendarDay;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public class OvertimeFragment extends BaseFragment {
    private static final int REQUEST_OVERTIME_CODE = 99;
    private static final String TAG = "OvertimeFragment";
    @BindView(R.id.tv_overtime_all_income)
    TextView mTvAllIncome;
    @BindView(R.id.tv_overtime_income)
    TextView mTvOvertimeIncome;
    @BindView(R.id.tv_overtime_hours)
    TextView mTvOvertimeHours;
    @BindView(R.id.btn_record_overtime)
    Button mBtnRecordOvertime;
    @BindView(R.id.recycler_checking_record)
    RecyclerView mRecyclerCheckingRecord;

    private CalendarDay mCalendarDay;

    private List<DayWorkInfo> mAllOvertimeInfo;

    @Override
    protected void initData() {
        mCalendarDay = CalendarDay.from(Calendar.getInstance());
//      获取当月总收入
        mTvAllIncome.setText(String.valueOf(SalaryOperationHelper.getFSalary(
                getContext(),
                mCalendarDay.getYear(),
                mCalendarDay.getMonth())));
//      获取当月加班总收入
        mTvOvertimeIncome.setText(String.valueOf(SalaryOperationHelper.getMonthOvertimeIncome(getContext(),
                mCalendarDay.getYear(),
                mCalendarDay.getMonth())));
//        获取当月加班总时数
        mTvOvertimeHours.setText(String.valueOf(SalaryOperationHelper.getMonthOvertimeHours(
                mCalendarDay.getYear(),
                mCalendarDay.getMonth())));

        mAllOvertimeInfo = DBOperatorHelper.getAllOvertimeInfo();
        mRecyclerCheckingRecord.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerCheckingRecord.setAdapter(new OverTimeCheckingInAdapter(mAllOvertimeInfo));
    }

    /**
     * 返回当日的工作日期
     *
     * @return
     */
    private DayWorkInfo getCurrentDayWorkInfo() {
        for (DayWorkInfo info : mAllOvertimeInfo) {
            if (info.getDatetime() == mCalendarDay.getDate().getTime()) {
                return info;
            }
        }
        return null;
    }

    @Override
    protected void initEvent() {
        mBtnRecordOvertime.setOnClickListener(this);
    }

    @Override
    protected void processClickListener(View view) {
        switch (view.getId()) {
            case R.id.btn_record_overtime:
//                判断有没有设置基本工资
                if (SPUtils.getFloat(getContext(), MyConstants.BASIC_SALARY, 0.0f) > 0.00) {
                    Intent intent = new Intent(getActivity(), RecordOvertimeActivity.class);
                    intent.putExtra(MyConstants.IS_FROM_CALENDAR, false);
                    intent.putExtra(MyConstants.SELECT_DAY_INFO, getCurrentDayWorkInfo());
                    startActivityForResult(intent, REQUEST_OVERTIME_CODE);
                } else {
                    ActivityUtils.startActivity(getActivity(), SalarySettingActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.fragment_overtime;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OVERTIME_CODE) {
            if (resultCode == MyConstants.RESULT_RECORD_OVERTIME_TO_CALENDAR) {
                mMainActivity.setTabSelected(1);
            }
        }
    }
}
