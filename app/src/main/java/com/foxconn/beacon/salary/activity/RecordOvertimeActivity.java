package com.foxconn.beacon.salary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.adapter.RecordOvertimeAdapter;
import com.foxconn.beacon.salary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe:
 */

public class RecordOvertimeActivity extends BaseActivity {
    @BindView(R.id.iv_record_back)
    ImageView mIvRecordBack;
    @BindView(R.id.recycler_record)
    RecyclerView mRecyclerRecord;

    @Override
    protected int getResId() {
        return R.layout.activity_record_overtime;
    }

    @Override
    protected void initData() {
        mRecyclerRecord.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerRecord.setAdapter(new RecordOvertimeAdapter());

    }

    @Override
    protected void initListener() {
        mIvRecordBack.setOnClickListener(this);
    }

    @Override
    protected void processClickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_record_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
