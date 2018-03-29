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
import com.foxconn.beacon.salary.utils.SPUtils;
import com.foxconn.beacon.salary.utils.UIUtils;

import java.util.Calendar;

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
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), StatisticsSettingActivity.class);
                Bundle mBundle = new Bundle();
                switch (view.getId()) {
//                    基本项目
                    case R.id.statisticsItem_basic_salary:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "基本工资设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 0);
                        break;
                    case R.id.statisticsItem_overtime_income:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "统计");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 1);
                        break;
                    case R.id.statisticsItem_adjustment:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "编辑调休");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 2);
                        break;
//                    补贴项目
//                    白班
                    case R.id.item_day_shift_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "白班补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 101);
                        break;
//                    中班
                    case R.id.item_middle_shift_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "中班补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 102);
                        break;
//                    晚班
                    case R.id.item_night_shift_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "晚班补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 103);
                        break;
//                    全勤奖
                    case R.id.item_attendance_bonus_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "全勤奖设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 104);
                        break;
//                    交通补贴
                    case R.id.item_traffic_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "交通补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 105);
                        break;
//                    餐补
                    case R.id.item_food_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "伙食补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 106);
                        break;
//                    生活补贴
                    case R.id.item_live_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "生活补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 107);
                        break;
//                    岗位补贴
                    case R.id.item_job_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "岗位补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 108);
                        break;
//                    高温补贴
                    case R.id.item_hyperthermia_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "高温补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 109);
                        break;
//                    环境补贴
                    case R.id.item_environment_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "环境补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 110);
                        break;
//                    绩效补贴
                    case R.id.item_performance_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "绩效补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 111);
                        break;
//                    其他补贴
                    case R.id.item_other_subsidy:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "其他补贴设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 112);
                        break;
                    case R.id.btn_statistics_add_subsidy:
                        mBundle = new Bundle();
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "添加收入项");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 100);
                        break;
//                    扣款项目
                    case R.id.item_things_leave:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "编辑事假");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 201);
                        break;
//                    病假
                    case R.id.item_sick_leave:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "编辑病假");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 202);
                        break;
//                    食堂消费
                    case R.id.item_mess_consume:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "食堂消费设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 203);
                        break;
//                    水电费
                    case R.id.item_utilities:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "水电费设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 204);
                        break;
//                    住宿费
                    case R.id.item_quarterage:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "住宿费设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 205);
                        break;
//                    其他扣款项目
                    case R.id.item_other_deduct:
                        mBundle.putString(MyConstants.STATISTICS_SETTING_TITLE, "其他扣款设置");
                        mBundle.putInt(MyConstants.STATISTICS_SETTING_LAYOUT, 206);
                        break;
//                    添加扣款项
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
                startActivityForResult(intent,MyConstants.REQUEST_STATISTICS_SETTING);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyConstants.REQUEST_STATISTICS_SETTING && resultCode == MyConstants.RESULT_STATISTICS_SETTING) {
            mStatisticsAdapter.notifyDataSetChanged();
        }
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
