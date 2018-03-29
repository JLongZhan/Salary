package com.foxconn.beacon.salary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class StatisticsItemView extends FrameLayout {

    private TextView mTvMoney;
    private TextView mTvItemName;

    public StatisticsItemView(@NonNull Context context) {
        this(context, null);
    }

    public StatisticsItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticsItemView);
        String itemName = typedArray.getString(R.styleable.StatisticsItemView_item_name);
        String itemMoney = typedArray.getString(R.styleable.StatisticsItemView_item_money);
        typedArray.recycle();


        View inflate = LayoutInflater.from(context).inflate(R.layout.view_statistics_project_item, null);
        mTvMoney = inflate.findViewById(R.id.tv_statistics_money);
        mTvMoney.setText(itemMoney);
        mTvItemName = inflate.findViewById(R.id.tv_statistics_item_name);
        mTvItemName.setText(itemName);
        addView(inflate);
    }

    /**
     * 设置金额
     *
     * @param money
     */
    public void setMoney(String money) {
        mTvMoney.setText(money);
    }

    /**
     * 设置每个Item的名称
     *
     * @param itemName
     */
    public void setItemName(String itemName) {
        mTvItemName.setText(itemName);
    }
}
