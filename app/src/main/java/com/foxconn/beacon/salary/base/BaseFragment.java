package com.foxconn.beacon.salary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: F1331886
 * @date: 2017/10/27 0027.
 * @describe:
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    private Unbinder mBind;

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

    /**
     * 实现点击事件
     * @param view
     */
    protected   void processClickListener(View view){

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
}
