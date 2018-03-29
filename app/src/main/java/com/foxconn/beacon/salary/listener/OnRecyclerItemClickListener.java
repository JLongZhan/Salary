package com.foxconn.beacon.salary.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author: F1331886
 * @date: 2017/11/8 0008.
 * @describe:
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private RecyclerView mRecyclerView;
    private final GestureDetectorCompat mCompat;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mCompat = new GestureDetectorCompat(recyclerView.getContext(), new OnGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mCompat.onTouchEvent(e);
    }

    public abstract void onItemClick(RecyclerView.ViewHolder childViewHolder);

    private class OnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            View childViewUnder = mRecyclerView.findChildViewUnder(x, y);
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                if (childViewHolder != null) {
                    onItemClick(childViewHolder);
                }
            }
            return super.onSingleTapUp(e);
        }

    }
}
