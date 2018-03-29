package com.foxconn.beacon.salary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
    private TextView mTvItemSubName;
    private TextView mTvItemSubValue;

    public StatisticsItemView(@NonNull Context context) {
        this(context, null);
    }

    public StatisticsItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticsItemView);
        String itemName = typedArray.getString(R.styleable.StatisticsItemView_item_left_name);
        String itemSubName = typedArray.getString(R.styleable.StatisticsItemView_item_left_sub_name);
        String itemSubValue = typedArray.getString(R.styleable.StatisticsItemView_item_right_name);
        String itemMoney = typedArray.getString(R.styleable.StatisticsItemView_item_right_name);
        boolean subNameShow = typedArray.getBoolean(R.styleable.StatisticsItemView_item_left_sub_name_visibility, false);
        boolean subValueShow = typedArray.getBoolean(R.styleable.StatisticsItemView_item_right_sub_name_visibility, false);
        int rightNameColor = typedArray.getColor(R.styleable.StatisticsItemView_item_right_name_color, Color.BLACK);
        typedArray.recycle();

        View inflate = LayoutInflater.from(context).inflate(R.layout.view_statistics_project_item, null);
        RelativeLayout rlParent = inflate.findViewById(R.id.rl_parent);
        mTvMoney = inflate.findViewById(R.id.tv_statistics_money);
        mTvMoney.setTextColor(rightNameColor);
        mTvMoney.setText(itemMoney);
        mTvItemName = inflate.findViewById(R.id.tv_statistics_item_name);
        mTvItemName.setText(itemName);
        mTvItemSubName = inflate.findViewById(R.id.tv_statistics_item_sub_name);
        mTvItemSubName.setText(itemSubName);
        mTvItemSubValue = inflate.findViewById(R.id.tv_statistics_item_sub_value);
        mTvItemSubValue.setText(itemSubValue);

        if (subNameShow) {
            mTvItemSubName.setVisibility(View.VISIBLE);
        } else {
            mTvItemSubName.setVisibility(View.GONE);
        }
        if (subValueShow) {
            mTvItemSubValue.setVisibility(View.VISIBLE);
        } else {
            mTvItemSubValue.setVisibility(View.GONE);
        }

        addView(inflate);
    }

    /**
     * 设置金额
     *
     * @param text
     */
    public void setRightText(String text) {
        mTvMoney.setText(text);
    }

    /**
     * 返回右侧文本数据
     *
     * @return
     */
    public String getRightText() {
        return mTvMoney.getText().toString().trim();
    }

    /**
     * 设置右侧
     *
     * @param subText
     */
    public void setLeftSubText(String subText) {
        mTvItemSubName.setVisibility(View.VISIBLE);
        mTvItemSubName.setText(subText);
    }

    /**
     * 设置右侧
     *
     * @param subText
     */
    public void setRightSubText(String subText) {
        mTvItemSubValue.setVisibility(VISIBLE);
        mTvItemSubValue.setText(subText);

    }

    /**
     * 设置每个Item的名称
     *
     * @param itemName
     */
    public void setLeftName(String itemName) {
        mTvItemName.setText(itemName);
    }

    public void setOnStatItemClickListener(OnClickListener listener) {
        this.setOnClickListener(listener);
    }
}
