package com.foxconn.beacon.salary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.utils.TextUtil;
import com.foxconn.beacon.salary.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: JLow
 * Date: 2017/10/9 0009.
 * Time:14:45
 * Describe:
 */

public class TimeLineView extends View {
    private static final String TAG = "TimeLineView";
    private List<String> mLabels = new ArrayList<>();
    private int mVSpace;
    private int mHSpace;
    private int mTextWidth = 0;
    private int mRadio;
    private Paint mPaint;
    private int mTextHeight;
    private int mCircleColor;
    private int mBarColor;
    private int mTextColor;
    /**
     * 选中的颜色
     */
    private int mSelectedColor;
    private int mSelectedPosition = 0;
    private int mTextSize;
    /**
     * 时间轴的宽度
     */
    private int mBarWidth;
    /**
     * 是否有发光效果
     */
    private boolean mIsShine;
    private Paint mCirclePaint;

    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Context context1 = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimeLineView, defStyleAttr, 0);

        mTextColor = ta.getColor(R.styleable.TimeLineView_text_color, Color.GRAY);
        mCircleColor = ta.getColor(R.styleable.TimeLineView_circle_color, ContextCompat.getColor(context1, android.R.color.holo_orange_dark));
        mBarColor = ta.getColor(R.styleable.TimeLineView_v_space_color, 0xff8a8a8a);
        mSelectedColor = ta.getColor(R.styleable.TimeLineView_circle_select_color, Color.GREEN);
        mIsShine = ta.getBoolean(R.styleable.TimeLineView_is_shine, true);
        mBarWidth = ta.getDimensionPixelOffset(R.styleable.TimeLineView_bar_width, 20);

        mVSpace = ta.getDimensionPixelSize(R.styleable.TimeLineView_v_space,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, displayMetrics));
        mHSpace = ta.getDimensionPixelSize(R.styleable.TimeLineView_h_space,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, displayMetrics));
        mTextSize = ta.getDimensionPixelSize(R.styleable.TimeLineView_text_size,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, displayMetrics));
        ta.recycle();

        init();
    }

    /**
     * 初始化属性
     */
    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);

        mTextHeight = TextUtil.getTextHeight(mPaint);
        mRadio = mTextHeight / 2;

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mSelectedColor);
        mCirclePaint.setMaskFilter(new BlurMaskFilter(mRadio, BlurMaskFilter.Blur.SOLID));


        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
        setLayerType(View.LAYER_TYPE_SOFTWARE, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getPaddingLeft() + mTextWidth + mHSpace + mRadio * 2 + getPaddingRight();
        int height = getPaddingTop() + mVSpace * mLabels.size() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    public int getRadio() {
        return mRadio;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        画柱
        mPaint.setColor(mBarColor);
        int left = getPaddingLeft() + mTextWidth + mHSpace;
        int top = getPaddingTop() + mRadio;
        int right = left + mBarWidth;
        int bottom = top + mVSpace * mLabels.size();
        canvas.drawRect(left, top, right, bottom, mPaint);
//        绘制圆和字体
        for (int i = 0; i < mLabels.size(); i++) {
            mPaint.setColor(mSelectedPosition == i ? mSelectedColor : mTextColor);
            canvas.drawText(mLabels.get(i),
                    getPaddingLeft(),
                    i * mVSpace + mTextHeight,
                    mPaint);
//            设置发光效果
            if (mIsShine) {
                mPaint.setColor(mSelectedPosition == i ? mSelectedColor : mCircleColor);
            }
            canvas.drawCircle(getPaddingLeft() + mTextWidth + mHSpace + mRadio / 2,
                    getPaddingTop() + mRadio + i * mVSpace + mRadio / 2,
                    mRadio,
                    mSelectedPosition == i ? mCirclePaint : mPaint);
        }
    }

    /**
     * 得到总高度
     *
     * @return
     */
    public int getTimeLineHeight() {
        return getPaddingTop() + mVSpace * mLabels.size() + getPaddingBottom();
    }

    public int getViewWidth() {
        return getPaddingLeft() + mTextWidth + mHSpace + mRadio * 2 + getPaddingRight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float y = event.getY();
                float v = (y) / (mVSpace);
                int pos = (int) v;
                if (y - pos * mVSpace < mRadio * 2) {
                    if (mSelectedPosition != pos) {
                        if (mOnLabelSelectChangeListener != null) {
                            mOnLabelSelectChangeListener.onLabelSelectChange(pos);
                        }
                        mSelectedPosition = pos;
                        invalidate();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置选中的标签
     *
     * @param position
     */
    public void setSelectedLabel(int position) {
        if (position < 0 || position > mLabels.size()) {
            return;
        }
        if (mSelectedPosition != position) {
            mSelectedPosition = position;
            invalidate();
        }
    }

    /**
     * 设置标签数据
     *
     * @param labels
     */
    public void setLabelData(@NonNull List<String> labels) {

        this.mLabels = labels;
        mTextWidth = getMaxTextWidth();
        invalidate();
    }

    private OnLabelSelectChangeListener mOnLabelSelectChangeListener;

    public void setOnLabelSelectChangeListener(OnLabelSelectChangeListener onLabelSelectChangeListener) {
        mOnLabelSelectChangeListener = onLabelSelectChangeListener;
    }

    public interface OnLabelSelectChangeListener {
        void onLabelSelectChange(int position);
    }

    /**
     * 得到最大的字体宽度
     *
     * @return
     */
    private int getMaxTextWidth() {
        int maxLength = TextUtil.getTextWidth(mPaint, mLabels.get(0));
        for (int i = 1; i < mLabels.size(); i++) {
            if (maxLength < TextUtil.getTextWidth(mPaint, mLabels.get(i))) {
                maxLength = TextUtil.getTextWidth(mPaint, mLabels.get(i));
            }
        }
        return maxLength;
    }
}
