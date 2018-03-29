package com.foxconn.beacon.salary.activity;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.fragment.CalendarFragment;
import com.foxconn.beacon.salary.fragment.StatisticsFragment;
import com.foxconn.beacon.salary.fragment.OvertimeFragment;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.beacon.materialcalendar.CalendarDay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foxconn.beacon.salary.R.id.fl_main_content;

/**
 * @author: F1331886
 * @date: 2017/10/24 0024.
 * @describe: 主界面
 */

public class MainActivity extends BaseActivity {
    @BindView(fl_main_content)
    FrameLayout mFlMainContent;
    @BindView(R.id.tab_layout_main)
    TabLayout mTabLayoutMain;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_toolbar_subtitle)
    TextView mTvToolbarSubtitle;
    @BindView(R.id.btn_toolbar_right)
    Button mBtnToolbarRight;
    @BindView(R.id.navigation_main)
    NavigationView mNavigationMenu;
    private List<BaseFragment> mContentFragments;
    private FragmentManager mFm;
    private OvertimeFragment mOvertimeFragment;
    private CalendarFragment mCalendarFragment;
    private StatisticsFragment mStatisticsFragment;

    @Override
    protected int getResId() {
        return R.layout.activity_main_content;
    }

    @Override
    protected void initData() {
        getFragment();
        chooseShow(0);
    }

    @Override
    protected void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        mTabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                chooseShow(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mNavigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
    }

    @Override
    protected void processClickEvent(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            default:
                break;
        }
    }

    /**
     * 设置选中一个TAB
     *
     * @param selectedId
     */
    public void setTabSelected(int selectedId) {
        mTabLayoutMain.setScrollPosition(selectedId, 1, false);
        mTabLayoutMain.getTabAt(selectedId).select();
    }

    /**
     * 初始化所有的内容
     */
    private void getFragment() {
        mFm = getSupportFragmentManager();

        FragmentTransaction transaction = mFm.beginTransaction();
        mOvertimeFragment = new OvertimeFragment();
        mCalendarFragment = new CalendarFragment();
        mStatisticsFragment = new StatisticsFragment();

        mContentFragments = new ArrayList<>();
        mContentFragments.add(mOvertimeFragment);
        mContentFragments.add(mCalendarFragment);
        mContentFragments.add(mStatisticsFragment);

        //全部添加到頁面中
        transaction.add(fl_main_content, mOvertimeFragment);
        transaction.add(fl_main_content, mCalendarFragment);
        transaction.add(fl_main_content, mStatisticsFragment);
        transaction.commit();
    }

    /**
     * 选择显示一个Fragment
     *
     * @param pos
     */
    private void chooseShow(int pos) {
        switch (pos) {
            case 0:
                mTvToolbarTitle.setText("工作周期");
                mTvToolbarTitle.setTextColor(UIUtils.getColor(R.color.colorBlack));
                String sb = String.valueOf(DateUtils.getCurrMonth()) + "月1日-" +
                        DateUtils.getCurrMonth() + "月" +
                        DateUtils.getDaysOfMonth(DateUtils.getCurrYear(), DateUtils.getCurrMonth()) + "日";
                setToolbarSubtitle(sb);
                break;
            case 1:
                mTvToolbarTitle.setTextColor(UIUtils.getColor(R.color.app_base_color));
                CalendarDay selectCalendarDay = ((CalendarFragment) mContentFragments.get(1)).getSelectCalendarDay();
                setCalendarPageTitle(selectCalendarDay.getYear(), selectCalendarDay.getMonth());
                break;
            default:
                mTvToolbarTitle.setText("统计");
                mTvToolbarTitle.setTextColor(UIUtils.getColor(R.color.app_base_color));
                mTvToolbarSubtitle.setVisibility(View.GONE);
                break;
        }
        mBtnToolbarRight.setVisibility(View.GONE);

        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        fragmentTransaction.hide(mOvertimeFragment);
        fragmentTransaction.hide(mCalendarFragment);
        fragmentTransaction.hide(mStatisticsFragment);
        fragmentTransaction.show(mContentFragments.get(pos));
        fragmentTransaction.commit();
    }

    public void setCalendarPageTitle(int year, int month) {
        mTvToolbarTitle.setText(month + 1 + "月");
        setToolbarSubtitle(String.valueOf(year));
    }


    /**
     * 设置Toolbar副标题
     *
     * @param subTitle
     */
    private void setToolbarSubtitle(String subTitle) {
        mTvToolbarSubtitle.setVisibility(View.VISIBLE);
        mTvToolbarSubtitle.setText(subTitle);
    }

}
