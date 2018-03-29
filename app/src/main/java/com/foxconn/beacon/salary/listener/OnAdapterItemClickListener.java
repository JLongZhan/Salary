package com.foxconn.beacon.salary.listener;

import android.view.View;

/**
 * @author: F1331886
 * @date: 2017/11/3 0003.
 * @describe:
 */

public interface OnAdapterItemClickListener {
    /**
     * @param view 点击的Item
     * @param position  索引
     */
    void onItemClick(View view, int position);
}
