package com.foxconn.beacon.salary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.activity.StatisticsSettingActivity;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.MonthSetting;
import com.foxconn.beacon.salary.utils.NumberUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/4 0004.
 * @describe:
 */

public abstract class BaseSettingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BaseSettingFragment";
    protected static final String PARAM1 = "param1";
    protected static final String PARAM2 = "param2";
    Unbinder mBind;
    protected StatisticsSettingActivity mActivity;
    public  MonthSetting mMonthSetting;
    protected static final DecimalFormat sDecimalFormat = new DecimalFormat("0.00");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View source = inflater.inflate(getContentResId(), container, false);
        mBind = ButterKnife.bind(this, source);
        return source;
    }

    /**
     * 获取Fragment内容视图的资源ID
     *
     * @return
     */
    protected abstract int getContentResId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (StatisticsSettingActivity) getActivity();
        mMonthSetting = DBOperatorHelper.getMonthSetting(mActivity.getCurrentYear(), mActivity.getCurrentMonth());
        initData();
        initEvent();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化事件监听
     */
    protected void initEvent() {
    }

    protected void showToast(String msg) {
        ToastUtils.showShort(getContext(), msg);
    }

    /**
     * 实现点击事件
     *
     * @param view
     */
    protected void processClickListener(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @Override
    public void onClick(View v) {
        processClickListener(v);
    }


    /**
     * 检查数字是否合格
     *
     * @param number
     * @return
     */
    protected boolean checkNumberFormat(String number, boolean isPercent) {
        if (!NumberUtils.isNumeric(number)) {
            ToastUtils.showShort(getActivity(), "请输入正确的数字");
            return false;
        }
        if (Float.parseFloat(number) < 0) {
            return false;
        }
        if (isPercent) {
            if (Float.parseFloat(number) > 100) {
                return false;
            }
        }
        return true;
    }

    /**
     * 保存数据
     */
    public boolean save() {
        return false;
    }
}
