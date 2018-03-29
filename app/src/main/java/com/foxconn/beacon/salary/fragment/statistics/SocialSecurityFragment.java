package com.foxconn.beacon.salary.fragment.statistics;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseSettingFragment;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.NumberUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;
import com.foxconn.beacon.salary.view.StatisticsItemView;

import butterknife.BindView;

/**
 * @author: F1331886
 * @date: 2017/11/6 0006.
 * @describe: 社保、公积金
 */

public class SocialSecurityFragment extends BaseSettingFragment {
    @BindView(R.id.statisticsItem_social_security_type)
    StatisticsItemView mStatisticsItemSocialSecurityType;
    @BindView(R.id.tv_social_security_value_name)
    TextView mTvSocialSecurityValueName;
    @BindView(R.id.et_social_security_value)
    EditText mEtSocialSecurityValue;
    @BindView(R.id.rl_adjust_hours)
    RelativeLayout mRlAdjustHours;
    private PopupWindow mPopupWindow;
    private ListView mLvPopupData;
    private String[] mChooseItems;
    private String mTitle;
    private String mTitle2;
    private int mCurrentValueType;

    /**
     * 打开一个Fragment 并传递一些参数
     *
     * @param param1
     * @param param2
     */
    public static SocialSecurityFragment startFragment(String param1, String param2) {
        SocialSecurityFragment socialSecurityFragment = new SocialSecurityFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM1, param1);
        bundle.putString(PARAM2, param2);
        socialSecurityFragment.setArguments(bundle);
        return socialSecurityFragment;
    }

    @Override
    protected int getContentResId() {
        return R.layout.fragment_socical_security;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(PARAM1);
            mStatisticsItemSocialSecurityType.setLeftName(mTitle);
            mTitle2 = arguments.getString(PARAM2);
        }
        mChooseItems = new String[]{"输入百分比", "输入金额"};
//        判断是社保还是公积金
        if (getString(R.string.SocialSecurity).equals(mTitle2)) {
            mCurrentValueType = mMonthSetting.getSocialSecurityType();
            mEtSocialSecurityValue.setText(mMonthSetting.getSocialSecurityValue() + "");
        } else {
            mCurrentValueType = mMonthSetting.getAccumulationFundType();
            mEtSocialSecurityValue.setText(mMonthSetting.getAccumulationFundValue() + "");
        }
        mTvSocialSecurityValueName.setText(mTitle2 + mChooseItems[mCurrentValueType]);
        mStatisticsItemSocialSecurityType.setRightText(mChooseItems[mCurrentValueType]);
        initPopupWindow();
    }

    /**
     * 设置数字的显示格式
     */
    private void setNumberShowStyle() {
        if (getString(R.string.SocialSecurity).equals(mTitle2)) {
            mEtSocialSecurityValue.setText(String.valueOf(mMonthSetting.getSocialSecurityValue()));
        } else {
            mEtSocialSecurityValue.setText(String.valueOf(mMonthSetting.getAccumulationFundValue()));
        }
        mEtSocialSecurityValue.setHint(mCurrentValueType == 0 ? "0.00%" : "0.0");
        mStatisticsItemSocialSecurityType.setRightText(mChooseItems[mCurrentValueType]);
    }

    @Override
    protected void initEvent() {
        mStatisticsItemSocialSecurityType.setOnStatItemClickListener(this);
        mLvPopupData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissWindow();
                mTvSocialSecurityValueName.setText(mTitle2 + mChooseItems[position]);
                mCurrentValueType = position;
                setNumberShowStyle();
            }
        });
    }

    @Override
    protected void processClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_popup_list_cancel:
                dismissWindow();
                break;
            case R.id.statisticsItem_social_security_type:
                showPopupWindow();
                break;
            default:
                break;
        }
    }
    /**
     * B保存数据
     */
    @Override
    public boolean save() {
        String trim = mEtSocialSecurityValue.getText().toString().trim();
        if (!checkNumberFormat(trim,false)) {
            ToastUtils.showShort(getActivity(), "请输入正确的数字");
            return false;
        }
        if (mCurrentValueType == 0) {
            if (Float.parseFloat(trim) > 100) {
                ToastUtils.showShort(getActivity(), "数字输入不正确！");
                return false;
            }
        } else {
            if (Float.parseFloat(trim) > SalaryOperationHelper.getBasicSalary(getContext(),
                    mActivity.getCurrentYear(), mActivity.getCurrentMonth())) {
                ToastUtils.showShort(getActivity(), "数字输入不正确！");
                return false;
            }
        }
        if (getString(R.string.SocialSecurity).equals(mTitle2)) {
            mMonthSetting.setSocialSecurityType(mCurrentValueType);
            mMonthSetting.setSocialSecurityValue(Float.parseFloat(trim));
        } else {
            mMonthSetting.setAccumulationFundType(mCurrentValueType);
            mMonthSetting.setAccumulationFundValue(Float.parseFloat(trim));
        }
        return true;
    }

    /**
     * 初始化窗口
     */
    private void initPopupWindow() {
        //        列表选择Dialog视图
        View listPopupView = View.inflate(getContext(), R.layout.popup_choose_list, null);
        ((TextView) listPopupView.findViewById(R.id.tv_popup_list_title)).setText(mTitle);
        mLvPopupData = listPopupView.findViewById(R.id.lv_popup_list_data);
        mLvPopupData.setAdapter(new ArrayAdapter<>(getContext(), R.layout.textview, R.id.tv_popup_item, mChooseItems));
        listPopupView.findViewById(R.id.tv_popup_list_cancel).setOnClickListener(this);

        mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(listPopupView);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = getActivity().getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1f;
                window.setAttributes(attributes);
            }
        });
        //使用默認的動畫樣式
        mPopupWindow.setAnimationStyle(R.style.animTranalate);
    }

    /**
     * 显示窗口
     */
    private void showPopupWindow() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.alpha = 0.3f;
            window.setAttributes(attributes);

            mPopupWindow.showAtLocation(mStatisticsItemSocialSecurityType, Gravity.BOTTOM, 0, 0);

        }
    }

    /**
     * 关闭窗口
     */
    private void dismissWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.alpha = 1f;
            window.setAttributes(attributes);
            mPopupWindow.dismiss();
        }
    }

}
