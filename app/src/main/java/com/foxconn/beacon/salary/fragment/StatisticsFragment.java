package com.foxconn.beacon.salary.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.activity.StatisticsSettingActivity;
import com.foxconn.beacon.salary.adapter.StatisticsAdapter;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.listener.OnAdapterItemClickListener;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:统计界面
 */

public class StatisticsFragment extends BaseFragment {
    private static final String TAG = "StatisticsFragment";
    @BindView(R.id.btn_statistics_previous)
    Button mBtnStatisticsPrevious;
    @BindView(R.id.btn_statistics_next)
    Button mBtnStatisticsNext;
    @BindView(R.id.recycler_statistics)
    RecyclerView mRecyclerStatistics;
    Unbinder unbinder;
    @BindView(R.id.tv_statistics_top_date)
    TextView mTvStatisticsTopDate;
    private int mCurrentMonth;
    private int mCurrentYear;
    private Calendar mCalendar;
    private StatisticsAdapter mStatisticsAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_count;
    }

    @Override
    protected void initData() {
        mCalendar = Calendar.getInstance();
        mCurrentMonth = DateUtils.getCurrMonth() - 1;
        mCurrentYear = DateUtils.getCurrYear();
        Log.i(TAG, "initData: " + Calendar.getInstance().get(Calendar.MONTH));
        setToolbar();
        setTopDateInfo(mCurrentYear, mCurrentMonth);
        mRecyclerStatistics.setLayoutManager(new LinearLayoutManager(mMainActivity));
        mStatisticsAdapter = new StatisticsAdapter(mCurrentYear, mCurrentMonth);
        mRecyclerStatistics.setAdapter(mStatisticsAdapter);
    }

    @Override
    protected void initEvent() {
        mBtnStatisticsNext.setOnClickListener(this);
        mBtnStatisticsPrevious.setOnClickListener(this);
        mStatisticsAdapter.setOnItemClickListener(new OnAdapterItemClickListener() {
            private Bundle mBundle;

            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticsSettingActivity.class);
                switch (view.getId()) {
//                    基本项目
                    case R.id.statisticsItem_basic_salary:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "基本工资设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 0);

                        break;
                    case R.id.statisticsItem_overtime_income:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "统计");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 1);
                        break;
                    case R.id.statisticsItem_adjustment:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "编辑调休");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 2);
                        break;
//                    补贴项目
//                    白班
                    case R.id.item_day_shift_subsidy:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "白班补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 100);
                        break;
//                    中班
                    case R.id.item_middle_shift_subsidy:

                        break;
                    //                    中班
                    case R.id.item_night_shift_subsidy:

                        break;
                    //                    中班
                    case R.id.item_attendance_bonus_subsidy:

                        break;
                    //                    中班
                    case R.id.item_traffic_subsidy:

                        break;
                    //                    中班
                    case R.id.item_food_subsidy:

                        break;
                    //                    中班
                    case R.id.item_live_subsidy:

                        break;
                    //                    中班
                    case R.id.item_job_subsidy:

                        break;
                    //                    中班
                    case R.id.item_hyperthermia_subsidy:

                        break;
                    //                    中班
                    case R.id.item_environment_subsidy:

                        break;
//                    中班
                    case R.id.item_performance_subsidy:

                        break;
//                    其他补贴
                    case R.id.item_other_subsidy:

                        break;

                    case R.id.btn_statistics_add_subsidy:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "添加收入项");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 100);
                        break;
//                    扣款项目
                    case R.id.item_things_leave:
                        mBundle = new Bundle();
                        break;
                    //                    病假
                    case R.id.item_sick_leave:
                        mBundle = new Bundle();
                        break;
//                    食堂消费
                    case R.id.item_mess_consume:
                        mBundle = new Bundle();
                        break;
//                    水电费
                    case R.id.item_utilities:
                        mBundle = new Bundle();
                        break;
//                    住宿费
                    case R.id.item_quarterage:
                        mBundle = new Bundle();
                        break;
//                    其他
                    case R.id.item_other_deduct:

                        break;
//                    扣款项
                    case R.id.btn_statistics_add_deduct:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "添加扣款项");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 200);
                        break;
//                    代缴费项目  社保
                    case R.id.statisticsItem_social_security:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "编辑社保");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 300);
                        break;
//                    公积金
                    case R.id.statisticsItem_accumulation_fund:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "编辑公积金");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 301);
                        break;
//                    个人所得税
                    case R.id.statisticsItem_income_tax:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "个人所得税设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 302);
                        break;
                    default:
                        break;
                }
                mBundle.putInt(MyConstants.CURRENT_YEAR, mCurrentYear);
                mBundle.putInt(MyConstants.CURRENT_MONTH, mCurrentMonth);
                intent.putExtras(mBundle);
                startActivityForResult(intent, MyConstants.REQUEST_STATISTICS_SETTING);
            }
        });
    }

    /**
     * 处理点击事件
     *
     * @param view
     */
    @Override
    protected void processClickListener(View view) {
        switch (view.getId()) {
//            上个月
            case R.id.btn_statistics_previous:
                mCurrentMonth--;
                if (mCurrentMonth < 0) {
                    mCurrentYear--;
                    mCurrentMonth = 11;
                }
                setTopDateInfo(mCurrentYear, mCurrentMonth);
                mStatisticsAdapter.refreshDate(mCurrentYear, mCurrentMonth);
                break;
            case R.id.btn_statistics_next:
                mCurrentMonth++;
                if (mCurrentMonth > 11) {
                    mCurrentYear++;
                    mCurrentMonth = 0;
                }
                setTopDateInfo(mCurrentYear, mCurrentMonth);
                mStatisticsAdapter.refreshDate(mCurrentYear, mCurrentMonth);
                break;
            default:
                break;
        }
    }

    /**
     * 设置当前显示的月份
     *
     * @param currentYear
     * @param currentMonth
     */
    private void setTopDateInfo(int currentYear, int currentMonth) {
        StringBuilder sb = new StringBuilder();
        mCalendar.set(Calendar.YEAR, currentYear);
        mCalendar.set(Calendar.MONTH, currentMonth);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        sb.append(DateUtils.date2String(mCalendar.getTime(), "MM月dd日"));
        sb.append(UIUtils.getString(R.string.space)).append("-").append(UIUtils.getString(R.string.space));
        mCalendar.set(Calendar.DAY_OF_MONTH, DateUtils.getDaysOfMonth(currentYear, currentMonth + 1));
        sb.append(DateUtils.date2String(mCalendar.getTime(), "MM月dd日"));
        mTvStatisticsTopDate.setText(sb.toString());
    }

    /**
     * 设置标题栏信息
     */
    private void setToolbar() {
        mMainActivity.setToolbarTitle("统计");
        mMainActivity.setToolbarTitleColor(UIUtils.getColor(R.color.app_base_color));
        mMainActivity.setToolbarSubTitleVisibility(false);
        mMainActivity.setToolbarRightVisibility(false);
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
