package com.foxconn.beacon.salary.fragment.statistics;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseSettingFragment;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/4 0004.
 * @describe: 加班统计界面
 */

public class OvertimeStatisticsFragment extends BaseSettingFragment {
    @BindView(R.id.tv_statistics_overtime_income)
    TextView mTvStatisticsOvertimeIncome;
    @BindView(R.id.tv_statistics_overtime_hours_duration)
    TextView mTvStatisticsOvertimeHoursDuration;
    @BindView(R.id.tv_statistics_overtime_day_count)
    TextView mTvStatisticsOvertimeDayCount;
    Unbinder unbinder;
    @BindView(R.id.tv_statistics_overtime_work_details)
    TextView mTvStatisticsOvertimeWorkDetails;
    @BindView(R.id.tv_statistics_overtime_work)
    TextView mTvStatisticsOvertimeWork;
    @BindView(R.id.rl_statistics_overtime_work)
    RelativeLayout mRlStatisticsOvertimeWork;
    @BindView(R.id.tv_statistics_overtime_rest_details)
    TextView mTvStatisticsOvertimeRestDetails;
    @BindView(R.id.tv_statistics_overtime_rest)
    TextView mTvStatisticsOvertimeRest;
    @BindView(R.id.rl_statistics_overtime_rest)
    RelativeLayout mRlStatisticsOvertimeRest;
    @BindView(R.id.tv_statistics_overtime_holiday_details)
    TextView mTvStatisticsOvertimeHolidayDetails;
    @BindView(R.id.tv_statistics_overtime_holiday)
    TextView mTvStatisticsOvertimeHoliday;
    @BindView(R.id.rl_statistics_overtime_holiday)
    RelativeLayout mRlStatisticsOvertimeHoliday;

    private float mWorkDateOvertimeHours;
    private float mRestDateOvertimeHours;
    private float mHolidayOvertimeHours;
    /**
     * 当月的所有加班记录
     */
    private List<DayWorkInfo> mMonthOvertimeInfo;


    @Override
    protected int getContentResId() {
        return R.layout.fragment_statistics_overtime;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
//        获取当月工作信息
        mMonthOvertimeInfo = DBOperatorHelper.getMonthOvertimeInfo(mActivity.getCurrentYear(), mActivity.getCurrentMonth());

        String income = String.valueOf(SalaryOperationHelper.getMonthOvertimeIncome(mActivity,
                mActivity.getCurrentYear(),
                mActivity.getCurrentMonth()));
        mTvStatisticsOvertimeIncome.setText(income);
//        设置工作小时
        String hours = String.valueOf(SalaryOperationHelper.getMonthOvertimeHours(mActivity.getCurrentYear(),
                mActivity.getCurrentMonth()));
        mTvStatisticsOvertimeHoursDuration.setText(hours);
//         设置工作天数
        String workDays = String.valueOf(mMonthOvertimeInfo.size());
        mTvStatisticsOvertimeDayCount.setText(workDays);

        categoryOvertime();

        showOvertimeDetails();
    }

    /**
     * 显示加班详情
     */
    private void showOvertimeDetails() {
        StringBuilder sb;
        if (mWorkDateOvertimeHours == 0.0) {
            mRlStatisticsOvertimeWork.setVisibility(View.GONE);
        } else {
            mRlStatisticsOvertimeWork.setVisibility(View.VISIBLE);
            sb = new StringBuilder();
            sb.append(SalaryOperationHelper.getWorkDateOvertimeSalary(getContext(), mActivity.getCurrentYear(), mActivity.getCurrentMonth())).append("元/小时")
                    .append(UIUtils.getString(R.string.space))
                    .append("x")
                    .append(UIUtils.getString(R.string.space))
                    .append(mWorkDateOvertimeHours)
                    .append("时");
            mTvStatisticsOvertimeWorkDetails.setText(sb.toString());
            float v = mWorkDateOvertimeHours * SalaryOperationHelper.getWorkDateOvertimeSalary(getContext(), mActivity.getCurrentYear(), mActivity.getCurrentMonth());
            mTvStatisticsOvertimeWork.setText("+" + v);
        }

        if (mRestDateOvertimeHours == 0.0) {
            mRlStatisticsOvertimeRest.setVisibility(View.GONE);
        } else {
            mRlStatisticsOvertimeRest.setVisibility(View.VISIBLE);
            sb = new StringBuilder();
            sb.append(SalaryOperationHelper.getWorkDateOvertimeSalary(getContext(), mActivity.getCurrentYear(), mActivity.getCurrentMonth())).append("元/小时")
                    .append(UIUtils.getString(R.string.space))
                    .append("x")
                    .append(UIUtils.getString(R.string.space))
                    .append(mRestDateOvertimeHours)
                    .append("时");
            mTvStatisticsOvertimeRestDetails.setText(sb.toString());
            float v = mRestDateOvertimeHours * SalaryOperationHelper.getWorkDateOvertimeSalary(getContext(), mActivity.getCurrentYear(), mActivity.getCurrentMonth());
            mTvStatisticsOvertimeRest.setText("+" + v);
        }

        if (mHolidayOvertimeHours == 0.0) {
            mRlStatisticsOvertimeHoliday.setVisibility(View.GONE);
        } else {
            mRlStatisticsOvertimeHoliday.setVisibility(View.VISIBLE);
            sb = new StringBuilder();
            sb.append(SalaryOperationHelper.getWorkDateOvertimeSalary(getContext(), mActivity.getCurrentYear(), mActivity.getCurrentMonth())).append("元/小时")
                    .append(UIUtils.getString(R.string.space))
                    .append("x")
                    .append(UIUtils.getString(R.string.space))
                    .append(mHolidayOvertimeHours)
                    .append("时");
            mTvStatisticsOvertimeHolidayDetails.setText(sb.toString());
            float v = mHolidayOvertimeHours * SalaryOperationHelper.getWorkDateOvertimeSalary(getContext(), mActivity.getCurrentYear(), mActivity.getCurrentMonth());
            mTvStatisticsOvertimeHoliday.setText("+" + v);
        }
    }

    /**
     * 对加班进行分类
     */
    private void categoryOvertime() {
        for (int i = 0; i < mMonthOvertimeInfo.size(); i++) {
            DayWorkInfo dayWorkInfo = mMonthOvertimeInfo.get(i);
            if (dayWorkInfo.getOvertimeType() == 1.5) {
                mWorkDateOvertimeHours += dayWorkInfo.getOvertimeDuration();
            } else if (dayWorkInfo.getOvertimeType() == 2.0) {
                mRestDateOvertimeHours += dayWorkInfo.getOvertimeDuration();
            } else {
                mHolidayOvertimeHours += dayWorkInfo.getOvertimeDuration();
            }
        }
    }
}
