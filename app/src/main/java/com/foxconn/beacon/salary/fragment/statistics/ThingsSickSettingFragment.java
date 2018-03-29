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
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/7 0007.
 * @describe:
 */

public class ThingsSickSettingFragment extends BaseSettingFragment {
    @BindView(R.id.tv_things_sick_title_title)
    TextView mTvThingsSickTitleTitle;
    @BindView(R.id.et_things_sick_percent)
    EditText mEtThingsSickPercent;
    @BindView(R.id.tv_things_sick_hours_salary)
    TextView mTvThingsSickHoursSalary;
    @BindView(R.id.tv_switch_name)
    TextView mTvSwitchName;
    @BindView(R.id.switch_statistics_setting)
    SwitchCompat mSwitchStatisticsSetting;
    @BindView(R.id.tv_things_sick_desc)
    TextView mTvThingsSickDesc;
    @BindView(R.id.tv_things_sick_auto_name)
    TextView mTvThingsSickAutoName;
    @BindView(R.id.et_things_sick_auto_value)
    EditText mEtThingsSickAutoValue;
    @BindView(R.id.rl_things_sick)
    RelativeLayout mRlThingsSick;
    Unbinder unbinder;
    private boolean mCurrentState;
    private String mCurrentText;
    private boolean mIsSick;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_things_sick_leave;
    }

    /**
     * @param isSickLeave
     * @return
     */
    public static ThingsSickSettingFragment startFragment(boolean isSickLeave) {
        ThingsSickSettingFragment fragment = new ThingsSickSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(PARAM2, isSickLeave);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        mTvThingsSickHoursSalary.setText(SalaryOperationHelper.getEachHourSalary(getContext(),
                mActivity.getCurrentYear(), mActivity.getCurrentMonth()) + "(元/小时)"
                + UIUtils.getString(R.string.space) + UIUtils.getString(R.string.space)
                + "x"
        );
//        是否是病假
        mIsSick = arguments.getBoolean(PARAM2);
        if (mIsSick) {
            mCurrentState = mMonthSetting.isCustomSickLeaveHours();
            mTvThingsSickTitleTitle.setText("病假扣除百分比");
            mTvThingsSickAutoName.setText("本月病假小时数");
            mCurrentText = mMonthSetting.getCustomSickLeaveHours() + "";
            mEtThingsSickPercent.setText(mMonthSetting.getSickLeavePercent() + "");
            mTvThingsSickDesc.setText(UIUtils.getString(R.string.sick_leave_description));
        } else {
            mCurrentState = mMonthSetting.isCustomThingsLeaveHours();
            mTvThingsSickTitleTitle.setText("事假假扣除百分比");
            mTvThingsSickAutoName.setText("本月事假小时数");
            mCurrentText = mMonthSetting.getCustomThingsLeaveHours() + "";
            mEtThingsSickPercent.setText(mMonthSetting.getThingsLeavePercent() + "");
            mTvThingsSickDesc.setText(UIUtils.getString(R.string.thing_leave_description));
        }
        onSwitchButtonClick(mCurrentState);
    }

    /**
     * 根据状态显示内容
     *
     * @param bool 是否自定义小时数目
     */
    private void onSwitchButtonClick(boolean bool) {
        //        读取之前的设置
        if (bool) {
            mSwitchStatisticsSetting.setChecked(false);
            mRlThingsSick.setVisibility(View.VISIBLE);
        } else {
            mSwitchStatisticsSetting.setChecked(true);
            mRlThingsSick.setVisibility(View.GONE);
        }
        mEtThingsSickAutoValue.setText(mCurrentText);
        mEtThingsSickAutoValue.setSelection((mCurrentText).length());
    }

    @Override
    protected void initEvent() {
        mSwitchStatisticsSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCurrentState = !mCurrentState;
                onSwitchButtonClick(mCurrentState);
            }
        });
    }

    @Override
    public boolean  save() {
        if (!mSwitchStatisticsSetting.isChecked()) {
            String customValue = mEtThingsSickAutoValue.getText().toString().trim();
            if (!checkNumberFormat(customValue, false)) {
                return false;
            }
            if (mIsSick) {
                mMonthSetting.setCustomSickLeaveHours(Float.parseFloat(customValue));
            } else {
                mMonthSetting.setCustomThingsLeaveHours(Float.parseFloat(customValue));
            }
        }
        String percent = mEtThingsSickPercent.getText().toString().trim();

        if (!checkNumberFormat(percent, true)) {
            return false;
        }
        if (mIsSick) {
            mMonthSetting.setCustomSickLeaveHours(!mSwitchStatisticsSetting.isChecked());
            mMonthSetting.setSickLeavePercent(Float.parseFloat(percent));
        } else {
            mMonthSetting.setCustomThingsLeaveHours(!mSwitchStatisticsSetting.isChecked());
            mMonthSetting.setThingsLeavePercent(Float.parseFloat(percent));
        }
        return true;
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
