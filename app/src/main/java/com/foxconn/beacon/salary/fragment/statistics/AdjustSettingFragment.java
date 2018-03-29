package com.foxconn.beacon.salary.fragment.statistics;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseSettingFragment;
import com.foxconn.beacon.salary.utils.NumberUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.foxconn.beacon.salary.view.StatisticsTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/11/4 0004.
 * @describe: 编辑调休
 */

public class AdjustSettingFragment extends BaseSettingFragment {

    private static final String TAG = "AdjustSettingFragment";
    @BindView(R.id.switch_statistics_setting)
    SwitchCompat mSwitchStatisticsSetting;
    @BindView(R.id.et_adjust_hours)
    EditText mEtAdjustHours;
    @BindView(R.id.statisticsTitle_adjust_offset)
    StatisticsTitleView mStatisticsTitleAdjustOffset;
    Unbinder unbinder;
    @BindView(R.id.rl_adjust_hours)
    RelativeLayout mRlAdjustHours;
    @BindView(R.id.tv_adjust_prompt)
    TextView mTvAdjustPrompt;
    @BindView(R.id.tv_switch_name)
    TextView mTvSwitchName;
    private ListView mLvPopupData;
    private PopupWindow mPopupWindow;
    private String[] mItems;
    private int mCurrentOffsetPosition;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_adjust_settting;
    }

    @Override
    protected void initData() {
        mItems = UIUtils.getArrays(R.array.adjust_offset);
        //        列表选择Dialog视图
        View listPopupView = View.inflate(getContext(), R.layout.popup_choose_list, null);
        ((TextView) listPopupView.findViewById(R.id.tv_popup_list_title)).setText("选择抵扣类型");
        mLvPopupData = listPopupView.findViewById(R.id.lv_popup_list_data);
        mLvPopupData.setAdapter(new ArrayAdapter<>(getContext(), R.layout.textview, R.id.tv_popup_item, mItems));
        listPopupView.findViewById(R.id.tv_popup_list_cancel).setOnClickListener(this);
        mPopupWindow = UIUtils.initPopupWindow(getActivity(), listPopupView);

        mSwitchStatisticsSetting.setChecked(!mMonthSetting.isCustomAdjustHours());
        mEtAdjustHours.setText(String.valueOf(mMonthSetting.getCustomAdjustHours()));
        mStatisticsTitleAdjustOffset.setTitleRightName(mItems[mMonthSetting.getAdjustOffsetType()]);
        mTvSwitchName.setText("自动获取调休小时数目");
        isCustomHoursVisibility(mSwitchStatisticsSetting.isChecked());

    }


    @Override
    protected void initEvent() {
        mSwitchStatisticsSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCustomHoursVisibility(isChecked);
            }
        });
        mStatisticsTitleAdjustOffset.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        mLvPopupData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStatisticsTitleAdjustOffset.setTitleRightName(mItems[position]);
                mCurrentOffsetPosition = position;
                dismissWindow();
            }
        });
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
            mPopupWindow.showAtLocation(mSwitchStatisticsSetting, Gravity.BOTTOM, 0, 0);

        }
    }

    /**
     * 关闭窗口
     */
    private void dismissWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 设置自定义调休小时是否可见
     */
    private void isCustomHoursVisibility(boolean isChecked) {
        if (isChecked) {
            mRlAdjustHours.setVisibility(View.GONE);
            mTvAdjustPrompt.setVisibility(View.GONE);
        } else {
            mRlAdjustHours.setVisibility(View.VISIBLE);
            mTvAdjustPrompt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void processClickListener(View view) {
        dismissWindow();
    }

    /**
     * 保存设置到数据库中
     */
    @Override
    public boolean save() {
        if (!mSwitchStatisticsSetting.isChecked()) {
            String trim = mEtAdjustHours.getText().toString().trim();
            if (!NumberUtils.isNumeric(trim)) {
                ToastUtils.showShort(getActivity(), "请输入正确的数值");
                return false;
            }
            mMonthSetting.setCustomAdjustHours(Float.parseFloat(trim));
        }
        mMonthSetting.setCustomAdjustHours(!mSwitchStatisticsSetting.isChecked());
        mMonthSetting.setAdjustOffsetType(mCurrentOffsetPosition);
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
