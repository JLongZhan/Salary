package com.foxconn.beacon.salary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe:
 */

public class StatisticsTitleView extends FrameLayout {

    private TextView mTvProjectName;
    private TextView mTvMoney;
    private static final String TAG = "StatisticsTitleView";
    private final String mProjectName;
    private final String mProjectMoney;

    public StatisticsTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticsTitleView);
        mProjectName = typedArray.getString(R.styleable.StatisticsTitleView_project_name);
        mProjectMoney = typedArray.getString(R.styleable.StatisticsTitleView_project_money);

        typedArray.recycle();

        View relativeLayout = LayoutInflater.from(context).inflate(R.layout.view_statistics_project_title, null);
        mTvProjectName = relativeLayout.findViewById(R.id.tv_statistics_project_name);
        mTvMoney = relativeLayout.findViewById(R.id.tv_statistics_money);
        addView(relativeLayout);

        mTvProjectName.setText(mProjectName);
        mTvMoney.setText(mProjectMoney);
    }


    /**
     * 设置项目名称
     *
     * @param text
     */
    public void setTvProjectName(String text) {
        mTvProjectName.setText(text);
    }

    /**
     * 设置金额
     *
     * @param money
     */
    public void setTvMoney(String money) {
        mTvMoney.setText(money);
    }
}
