package com.foxconn.beacon.salary.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.foxconn.beacon.salary.utils.StateBarTranslucentUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;
import com.foxconn.beacon.salary.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected static String TAG;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            StateBarTranslucentUtils.setStateBarTranslucent(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        setContentView(getResId());
        mUnbinder = ButterKnife.bind(this);
        TAG = getClass().getSimpleName();
        initContentView();
        initData();
        initListener();
    }

    /**
     * 初始化内容视图
     */
    protected void initContentView() {
    }

    /**
     * 获取XML布局资源
     *
     * @return
     */
    protected abstract int getResId();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    protected  void showToast(String msg){
        ToastUtils.showShort(UIUtils.getContext(), msg);
    }
    /**
     * 处理点击事件
     *
     * @param view
     */
    protected void processClickEvent(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        processClickEvent(v);
    }
}
