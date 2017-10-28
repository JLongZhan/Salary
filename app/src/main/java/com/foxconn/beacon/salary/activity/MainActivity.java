package com.foxconn.beacon.salary.activity;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.base.BaseFragment;
import com.foxconn.beacon.salary.fragment.CalendarFragment;
import com.foxconn.beacon.salary.fragment.CountFragment;
import com.foxconn.beacon.salary.fragment.OvertimeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: F1331886
 * @date: 2017/10/24 0024.
 * @describe: 主界面
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_main_content)
    FrameLayout mFlMainContent;
    @BindView(R.id.tab_layout_main)
    TabLayout mTabLayoutMain;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private List<BaseFragment> mContentFragments;
    private FragmentManager mFm;

    @Override
    protected int getResId() {
        return R.layout.activity_main_content;
    }

    @Override
    protected void initContentView() {
    }

    @Override
    protected void initData() {
        getFragment();
//        默认选中
        mFm.beginTransaction().replace(R.id.fl_main_content, mContentFragments.get(0)).commit();
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
                mFm.beginTransaction()
                        .replace(R.id.fl_main_content, mContentFragments.get(tab.getPosition()))
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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
     * 初始化所有的内容
     */
    private void getFragment() {
        mFm = getSupportFragmentManager();
        mContentFragments = new ArrayList<>();
        mContentFragments.add(new OvertimeFragment());
        mContentFragments.add(new CalendarFragment());
        mContentFragments.add(new CountFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
