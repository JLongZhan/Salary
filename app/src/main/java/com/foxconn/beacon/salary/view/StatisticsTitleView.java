package com.foxconn.beacon.salary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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
    private final View mRelativeLayout;

    public StatisticsTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticsTitleView);
        String projectName = typedArray.getString(R.styleable.StatisticsTitleView_title_left_name);
        String projectMoney = typedArray.getString(R.styleable.StatisticsTitleView_title_right_name);
        int color = typedArray.getColor(R.styleable.StatisticsTitleView_title_view_background, 0xffF0F0F0);
        int leftNameColor = typedArray.getColor(R.styleable.StatisticsTitleView_title_left_color, 0xffaaaaaa);
        int rightNameColor = typedArray.getColor(R.styleable.StatisticsTitleView_title_right_color, 0xffaaaaaa);

        typedArray.recycle();

        mRelativeLayout = LayoutInflater.from(context).inflate(R.layout.view_statistics_project_title, null);
        mRelativeLayout.setBackgroundColor(color);
        mTvProjectName = mRelativeLayout.findViewById(R.id.tv_statistics_project_name);
        mTvMoney = mRelativeLayout.findViewById(R.id.tv_statistics_money);
        addView(mRelativeLayout);

        mTvProjectName.setText(projectName);
        mTvProjectName.setTextColor(leftNameColor);
        mTvMoney.setText(projectMoney);
        mTvMoney.setTextColor(rightNameColor);
    }

    public void setOnTitleClickListener(View.OnClickListener listener) {
        setOnClickListener(listener);
    }

    public void setTitleBackground(int color) {
        mRelativeLayout.setBackgroundColor(color);
    }

    /**
     * 设置项目名称
     *
     * @param text
     */
    public void setTitleLeftName(String text) {
        mTvProjectName.setText(text);
    }

    /**
     * 设置金额
     *
     * @param rightName
     */
    public void setTitleRightName(String rightName) {
        mTvMoney.setText(rightName);
    }
}
