package com.foxconn.beacon.salary.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.base.BaseSettingFragment;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.fragment.calendar.OvertimeDetailsFragment;
import com.foxconn.beacon.salary.fragment.statistics.AdjustSettingFragment;
import com.foxconn.beacon.salary.fragment.statistics.BasicSalarySettingFragment;
import com.foxconn.beacon.salary.fragment.statistics.CommonSubsidySetting;
import com.foxconn.beacon.salary.fragment.statistics.DeductProjectFragment;
import com.foxconn.beacon.salary.fragment.statistics.OvertimeStatisticsFragment;
import com.foxconn.beacon.salary.fragment.statistics.SocialSecurityFragment;
import com.foxconn.beacon.salary.fragment.statistics.ThingsSickSettingFragment;
import com.foxconn.beacon.salary.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: F1331886
 * @date: 2017/11/4 0004.
 * @describe:
 */

public class StatisticsSettingActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_toolbar_subtitle)
    TextView mTvToolbarSubtitle;
    @BindView(R.id.btn_toolbar_right)
    Button mBtnToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_commend_content)
    LinearLayout mFlCommendContent;
    @BindView(R.id.btn_record_save)
    Button mBtnRecordSave;
    private String mTitle;
    private int mLayout;
    private int mCurrentYear;
    private int mCurrentMonth;
    private BaseSettingFragment mFragment;

    @Override
    protected int getResId() {
        return R.layout.activity_commend;
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTitle = extras.getString(MyConstants.STATISTICS_SETTING_TITLE);
            mLayout = extras.getInt(MyConstants.STATISTICS_SETTING_LAYOUT);
            mCurrentYear = extras.getInt(MyConstants.CURRENT_YEAR);
            mCurrentMonth = extras.getInt(MyConstants.CURRENT_MONTH);
        }
        mTvToolbarTitle.setText(mTitle);
//        替换布局
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (mLayout) {
            case 0:
                mFragment = BasicSalarySettingFragment.startFragment("基本工资");
                break;
//            加班工资
            case 1:
                mFragment = new OvertimeStatisticsFragment();
                mBtnRecordSave.setVisibility(View.GONE);
                break;
//            编辑调休
            case 2:
                mFragment = new AdjustSettingFragment();
                break;
//            收入项按钮
            case 100:
                mFragment = DeductProjectFragment.startFragment(UIUtils.getArrays(R.array.income_project), false);
                break;
            case 101:
                mFragment = CommonSubsidySetting.startFragment("白班补贴", new String[]{"getDayShiftSubsidy", "setDayShiftSubsidy"});
                break;
            case 102:
                mFragment = CommonSubsidySetting.startFragment("中班补贴", new String[]{"getMiddleShiftSubsidy", "setMiddleShiftSubsidy"});
                break;
            case 103:
                mFragment = CommonSubsidySetting.startFragment("晚班补贴", new String[]{"getNightShiftSubsidy", "setNightShiftSubsidy"});
                break;
            case 104:
                mFragment = CommonSubsidySetting.startFragment("全勤奖", new String[]{"getAttendanceBonus", "setAttendanceBonus"});
                break;
            case 105:
                mFragment = CommonSubsidySetting.startFragment("交通补贴", new String[]{"getTrafficSubsidy", "setTrafficSubsidy"});
                break;
            case 106:
                mFragment = CommonSubsidySetting.startFragment("伙食补贴", new String[]{"getFoodSubsidy", "setFoodSubsidy"});
                break;
            case 107:
                mFragment = CommonSubsidySetting.startFragment("生活补贴", new String[]{"getLiveSubsidy", "setLiveSubsidy"});
                break;
            case 108:
                mFragment = CommonSubsidySetting.startFragment("岗位补贴", new String[]{"getJobSubsidy", "setJobSubsidy"});
                break;
            case 109:
                mFragment = CommonSubsidySetting.startFragment("高温补贴", new String[]{"getHyperthermiaSubsidy", "setHyperthermiaSubsidy"});
                break;
            case 110:
                mFragment = CommonSubsidySetting.startFragment("环境补贴", new String[]{"getEnvironmentSubsidy", "setEnvironmentSubsidy"});
                break;
            case 111:
                mFragment = CommonSubsidySetting.startFragment("绩效补贴", new String[]{"getPerformanceSubsidy", "setPerformanceSubsidy"});
                break;
            case 112:
                mFragment = CommonSubsidySetting.startFragment("其他补贴", new String[]{"getOtherSubsidy", "setOtherSubsidy"});
                break;
//            扣款项按钮
            case 200:
                mFragment = DeductProjectFragment.startFragment(UIUtils.getArrays(R.array.deduct_project), true);
                break;
            case 201:
                mFragment = ThingsSickSettingFragment.startFragment(false);
                break;
            case 202:
                mFragment = ThingsSickSettingFragment.startFragment(true);
                break;
            case 203:
                mFragment = CommonSubsidySetting.startFragment("食堂消费", new String[]{"getMessConsume", "setMessConsume"});
                break;
            case 204:
                mFragment = CommonSubsidySetting.startFragment("水电费", new String[]{"getUtilities", "setUtilities"});
                break;
            case 205:
                mFragment = CommonSubsidySetting.startFragment("住宿费", new String[]{"getQuarterage", "setQuarterage"});
                break;
            case 206:
                mFragment = CommonSubsidySetting.startFragment("其他消费", new String[]{"getOtherDeduct", "setOtherDeduct"});
                break;
            case 300:
                mFragment = SocialSecurityFragment.startFragment("社保设定方式", "社保");
                break;
            case 301:
                mFragment = SocialSecurityFragment.startFragment("公积金设定方式", "公积金");
                break;
            case 302:
                mFragment = BasicSalarySettingFragment.startFragment("个人所得税");
                break;
            default:
                mFragment = OvertimeDetailsFragment.startFragment();
                mBtnRecordSave.setVisibility(View.GONE);
                break;
        }
        fragmentTransaction.replace(R.id.fl_commend_content, mFragment).commit();
    }

    @Override
    protected void initListener() {
        mBtnRecordSave.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initContentView() {
        initToolbar();
    }


    @Override
    protected void processClickEvent(View view) {
        if (mFragment.save()) {
            mFragment.mMonthSetting.save();
            setResult(MyConstants.RESULT_STATISTICS_SETTING);
            finish();
        } else {
            return;
        }
    }

    /**
     * 初始化Toolbar信息
     */
    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mTvToolbarSubtitle.setVisibility(View.GONE);
    }

    /**
     * 返回当前选中的月份
     *
     * @return
     */
    public int getCurrentMonth() {
        return mCurrentMonth;
    }

    /**
     * 返回当前选中的年份
     *
     * @return
     */
    public int getCurrentYear() {
        return mCurrentYear;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
