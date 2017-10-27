package com.foxconn.beacon.salary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.FrameLayout;

import com.foxconn.beacon.salary.base.BaseActivity;

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


    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        mTabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabSelected: " + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
