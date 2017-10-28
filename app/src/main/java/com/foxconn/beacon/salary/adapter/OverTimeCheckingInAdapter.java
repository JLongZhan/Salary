package com.foxconn.beacon.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.view.TimeLineView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: F1331886
 * @date: 2017/10/28 0028.
 * @describe:
 */

public class OverTimeCheckingInAdapter extends RecyclerView.Adapter {
    List<String> mStrings;

    public OverTimeCheckingInAdapter(List<String> datas) {
        mStrings = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checking_in_record, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mStrings == null ? 0 : mStrings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TimeLineView timeLineView;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
