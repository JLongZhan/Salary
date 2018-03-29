package com.foxconn.beacon.salary.fragment.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.activity.RecordOvertimeActivity;
import com.foxconn.beacon.salary.adapter.OvertimeDetailsAdapter;
import com.foxconn.beacon.salary.base.BaseSettingFragment;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.listener.OnRecyclerItemClickListener;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.UIUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/8 0008.
 * @describe:  加班详情
 */

public class OvertimeDetailsFragment extends BaseSettingFragment {

    private static final String TAG = "OvertimeDetailsFragment";
    @BindView(R.id.tabLayout_overtime_details)
    TabLayout mTabLayoutOvertimeDetails;
    @BindView(R.id.recycler_overtime_details)
    RecyclerView mRecyclerOvertimeDetails;
    Unbinder unbinder;
    @BindView(R.id.btn_statistics_previous)
    Button mBtnStatisticsPrevious;
    @BindView(R.id.tv_statistics_top_date)
    TextView mTvStatisticsTopDate;
    @BindView(R.id.btn_statistics_next)
    Button mBtnStatisticsNext;
    @BindView(R.id.tv_overtime_details_noData)
    TextView mTvNoData;
    private OvertimeDetailsAdapter mAdapter;
    private Calendar mCalendar;
    private int mCurrentMonth;
    private int mCurrentYear;
    private List<DayWorkInfo> mMonthOvertimeInfo;
    private List<DayWorkInfo> mMonthLeaveInfo;
    private boolean hasData;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_overtime_details;
    }

    /**
     * @return
     */
    public static OvertimeDetailsFragment startFragment() {
        OvertimeDetailsFragment fragment = new OvertimeDetailsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        mCalendar = Calendar.getInstance();
        mCurrentMonth = mActivity.getCurrentMonth();
        mCurrentYear = mActivity.getCurrentYear();
        setTopDateInfo(mActivity.getCurrentYear(), mActivity.getCurrentMonth());

        mRecyclerOvertimeDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerOvertimeDetails.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        默认选中加班
        onOvertimeTabSelect();
    }

    /**
     * 初始化
     */
    private void refreshData(List info) {
        if (mAdapter == null) {
            mAdapter = new OvertimeDetailsAdapter(info, true);
            mRecyclerOvertimeDetails.setAdapter(mAdapter);
        } else {
            mAdapter.refreshData(info, mTabLayoutOvertimeDetails.getSelectedTabPosition() == 0);
        }
    }

    /**
     * 加班的TAB被选中
     */
    private void onOvertimeTabSelect() {
        hasData = false;
        Iterator<DayWorkInfo> iterator = mMonthOvertimeInfo.iterator();
        while (iterator.hasNext()) {
            DayWorkInfo next = iterator.next();
            if (next.getOvertimeDuration() != 0) {
                hasData = true;
            } else {
                iterator.remove();
            }
        }
        chooseShow(mMonthOvertimeInfo);
    }

    /**
     * 请假的TAB被选中
     */
    private void onLeaveTabSelect() {
        hasData = false;
        Iterator<DayWorkInfo> iterator = mMonthLeaveInfo.iterator();
        while (iterator.hasNext()) {
            DayWorkInfo next = iterator.next();
            if (next.getLeaveType() > 0) {
                hasData = true;
            } else {
                iterator.remove();
            }
        }
        chooseShow(mMonthLeaveInfo);
    }

    /**
     * 选择显示
     */
    private void chooseShow(List<DayWorkInfo> info) {
        if (hasData) {
            mTvNoData.setVisibility(View.GONE);
            mRecyclerOvertimeDetails.setVisibility(View.VISIBLE);
            refreshData(info);
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
            if (mTabLayoutOvertimeDetails.getSelectedTabPosition() == 0) {
                mTvNoData.setText("没有加班信息");
            } else {
                mTvNoData.setText("没有请假信息");
            }
            mRecyclerOvertimeDetails.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvent() {
        mBtnStatisticsPrevious.setOnClickListener(this);
        mBtnStatisticsNext.setOnClickListener(this);

        mRecyclerOvertimeDetails.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerOvertimeDetails) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder childViewHolder) {
                int adapterPosition = childViewHolder.getAdapterPosition();
                Intent intent = new Intent(getActivity(), RecordOvertimeActivity.class);
                intent.putExtra(MyConstants.IS_FROM_CALENDAR, true);
                intent.putExtra(MyConstants.SELECT_DATE, mMonthOvertimeInfo.get(adapterPosition).getDatetime());
                intent.putExtra(MyConstants.SELECT_DAY_INFO, mMonthOvertimeInfo.get(adapterPosition));
                startActivity(intent);
            }
        });

        mTabLayoutOvertimeDetails.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    onOvertimeTabSelect();
                } else {
                    onLeaveTabSelect();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

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
                break;
            case R.id.btn_statistics_next:
                mCurrentMonth++;
                if (mCurrentMonth > 11) {
                    mCurrentYear++;
                    mCurrentMonth = 0;
                }
                break;
            default:
                break;
        }
        setTopDateInfo(mCurrentYear, mCurrentMonth);
        if (mTabLayoutOvertimeDetails.getSelectedTabPosition() == 0) {
            onOvertimeTabSelect();
        } else {
            onLeaveTabSelect();
        }
    }

    /**
     * 设置当前显示的月份
     *
     * @param currentYear
     * @param currentMonth
     */

    private void setTopDateInfo(int currentYear, int currentMonth) {
        mMonthLeaveInfo = new ArrayList<>();
        mMonthOvertimeInfo = DBOperatorHelper.getMonthOvertimeInfo(mCurrentYear, mCurrentMonth);
        Collections.copy(mMonthOvertimeInfo, mMonthLeaveInfo);
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
