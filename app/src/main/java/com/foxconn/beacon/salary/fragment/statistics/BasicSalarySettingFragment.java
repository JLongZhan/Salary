package com.foxconn.beacon.salary.fragment.statistics;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseSettingFragment;
import com.foxconn.beacon.salary.utils.NumberUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/4 0004.
 * @describe: 基本工资设置
 */

public class BasicSalarySettingFragment extends BaseSettingFragment {
    @BindView(R.id.tv_basic_salary_setting_title)
    TextView mTvBasicSalarySettingTitle;
    @BindView(R.id.switch_statistics_setting)
    SwitchCompat mSwitchStatisticsSetting;
    @BindView(R.id.et_statistics_setting_money)
    EditText mEtStatisticsSettingMoney;
    Unbinder unbinder;
    @BindView(R.id.rl_statistics_setting_item_parent)
    RelativeLayout mRlStatisticsSettingItemParent;
    @BindView(R.id.tv_switch_name)
    TextView mTvSwitchName;
    @BindView(R.id.tv_subsidy_title)
    TextView mTvSubsidyTitle;
    private String mString;

    public static BasicSalarySettingFragment startFragment(String param1) {
        BasicSalarySettingFragment fragment = new BasicSalarySettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM1, param1);
        bundle.putString(PARAM2, "");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentResId() {
        return R.layout.fragment_basic_salary_setting;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mString = arguments.getString(PARAM1);
        }
        mSwitchStatisticsSetting.setChecked(true);
        mTvSwitchName.setText("自动获取" + mString);
        mTvSubsidyTitle.setText("手动输入" + mString);
//        从数据库中获取之前的设置
        mSwitchStatisticsSetting.setChecked(!mMonthSetting.isCustomBasicSalary());
        mEtStatisticsSettingMoney.setText(String.valueOf(mMonthSetting.getCustomBasicSalary()));
        autoGetSalary(mSwitchStatisticsSetting.isChecked());
    }
    /**
     * 自动获取基本工资
     */
    private void autoGetSalary(boolean isAutoGetSalary) {
        if (isAutoGetSalary) {
            mTvBasicSalarySettingTitle.setText("自动获取" + mString);
            mRlStatisticsSettingItemParent.setVisibility(View.GONE);
        } else {
            mTvBasicSalarySettingTitle.setText("自定义的" + mString);
            mRlStatisticsSettingItemParent.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void initEvent() {
        mSwitchStatisticsSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoGetSalary(isChecked);
            }
        });
    }

    /**
     * 保存数据
     */
    @Override
    public boolean save() {
        if (!mSwitchStatisticsSetting.isChecked()) {
            String trim = mEtStatisticsSettingMoney.getText().toString().trim();
            if (!checkNumberFormat(trim,false)) {
                return false;
            }
            mMonthSetting.setCustomBasicSalary(Float.parseFloat(trim));
        }
        mMonthSetting.setCustomBasicSalary(!mSwitchStatisticsSetting.isChecked());
        return true;
    }
}
