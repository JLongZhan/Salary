package com.foxconn.beacon.salary.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.adapter.OverTimeCheckingInAdapter;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public class OvertimeFragment extends BaseFragment {
    @BindView(R.id.tv_overtime_all_income)
    TextView mTvOvertimeAllIncome;
    @BindView(R.id.tv_overtime_income)
    TextView mTvOvertimeIncome;
    @BindView(R.id.tv_overtime_hours)
    TextView mTvOvertimeHours;
    @BindView(R.id.btn_record_overtime)
    Button mBtnRecordOvertime;
    @BindView(R.id.recycler_checking_record)
    RecyclerView mRecyclerCheckingRecord;

    private List<String> mList;

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add("09.01");
        mList.add("09.02");
        mList.add("09.03");
        mList.add("09.04");
        mList.add("09.05");
        mList.add("09.06");
        mList.add("09.07");
        mRecyclerCheckingRecord.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerCheckingRecord.setAdapter(new OverTimeCheckingInAdapter(mList));
    }

    @Override
    protected void initEvent() {
        mBtnRecordOvertime.setOnClickListener(this);
    }

    @Override
    protected void processClickListener(View view) {
        switch (view.getId()) {
            case R.id.btn_record_overtime:
                ToastUtils.showShort(getContext(), "等会再记录吧");
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.fragment_overtime;
    }
}
