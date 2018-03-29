package com.foxconn.beacon.salary.fragment.statistics;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseSettingFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/7 0007.
 * @describe:
 */

public class CommonSubsidySetting extends BaseSettingFragment {
    @BindView(R.id.tv_subsidy_title)
    TextView mTvSubsidyTitle;
    @BindView(R.id.et_statistics_setting_money)
    EditText mEtStatisticsSettingMoney;
    @BindView(R.id.rl_statistics_setting_item_parent)
    RelativeLayout mRlStatisticsSettingItemParent;
    @BindView(R.id.tv_money_unit)
    TextView mTvMoneyUnit;
    Unbinder unbinder;
    private String[] mMethodName;
    private static final String TAG = "CommonSubsidySetting";

    @Override
    protected int getContentResId() {
        return R.layout.fragment_subsidy_setting;
    }

    /**
     * 开启一个Fragment
     *
     * @param object
     * @return
     */
    public static CommonSubsidySetting startFragment(String object, String[] methodName) {
        CommonSubsidySetting fragment = new CommonSubsidySetting();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM1, object);
        bundle.putStringArray(PARAM2, methodName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String string = arguments.getString(PARAM1);
        mMethodName = arguments.getStringArray(PARAM2);
        assert string != null;
        if (string.contains("白班") || string.contains("中班") || string.contains("晚班")) {
            mTvMoneyUnit.setText("元/天");
        }
        mTvSubsidyTitle.setText(string);
        try {
            Method method = mMonthSetting.getClass().getDeclaredMethod(mMethodName[0]);
            Object invoke = method.invoke(mMonthSetting);
            mEtStatisticsSettingMoney.setText(invoke + "");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean save() {
        String s = mEtStatisticsSettingMoney.getText().toString().trim();
        if (!checkNumberFormat(s, false)) {
            return false;
        }
        try {
            Method method = mMonthSetting.getClass().getDeclaredMethod(mMethodName[1], float.class);
            method.invoke(mMonthSetting, Float.parseFloat(sDecimalFormat.format(Float.valueOf(s))));
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        assert rootView != null;
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
