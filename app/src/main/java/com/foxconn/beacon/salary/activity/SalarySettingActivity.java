package com.foxconn.beacon.salary.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.utils.SPUtils;
import com.foxconn.beacon.salary.view.StatisticsItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: F1331886
 * @date: 2017/11/2 0002.
 * @describe:      工资设置界面
 */

public class SalarySettingActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_toolbar_subtitle)
    TextView mTvToolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.statisticsItem_checking_circle)
    StatisticsItemView mStatisticsItemCheckingCircle;
    @BindView(R.id.statisticsItem_position_manage)
    StatisticsItemView mStatisticsItemPositionManage;
    @BindView(R.id.et_salary_setting)
    EditText mEtSalarySetting;
    @BindView(R.id.btn_salary_setting_save)
    Button mBtnSave;


    @Override
    protected int getResId() {
        return R.layout.activity_salary_setting;
    }

    @Override
    protected void initData() {
        initToolbar();
    }

    @Override
    protected void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mStatisticsItemCheckingCircle.setOnStatItemClickListener(this);
        mStatisticsItemPositionManage.setOnStatItemClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    /**
     * 点击事件的处理
     *
     * @param view
     */
    @Override
    protected void processClickEvent(View view) {
        switch (view.getId()) {
            case R.id.statisticsItem_checking_circle:

                break;
            case R.id.statisticsItem_position_manage:

                break;
            case R.id.btn_salary_setting_save:
                String trim = mEtSalarySetting.getText().toString().trim();
                if (!TextUtils.isEmpty(trim) && Float.valueOf(trim) > 0.0f) {
//                    保存基本工资
                    SPUtils.put(this, MyConstants.BASIC_SALARY, Float.valueOf(trim));
                    finish();
                    break;
                }
                return;
            default:
                break;
        }
    }

    /**
     * Toolbar的信息设置
     */
    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mTvToolbarSubtitle.setVisibility(View.GONE);
        mTvToolbarTitle.setText("工资设置");
    }
}
