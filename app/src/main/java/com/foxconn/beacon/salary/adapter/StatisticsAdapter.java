package com.foxconn.beacon.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.R;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe:
 */

public class StatisticsAdapter extends RecyclerView.Adapter {
    private static final String TAG = "StatisticsAdapter";
    private static final int VIEW_TYPE_ALL_INCOMING = 0;
    private static final int VIEW_TYPE_BASIC_PROJECT = 1;
    private static final int VIEW_TYPE_SUBSIDY_PROJECT = 2;
    private static final int VIEW_TYPE_DEDUCT_PROJECT = 3;
    private static final int VIEW_TYPE_AGENCY_PROJECT = 4;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate;
        switch (viewType) {
//            总收入
            case VIEW_TYPE_ALL_INCOMING:
                inflate = layoutInflater.inflate(R.layout.item_statistics_all_incoming, parent, false);
                return new AllIncomingViewHolder(inflate);
//            基本项目
            case VIEW_TYPE_BASIC_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_basic_project, parent, false);
                return new BasicProjectViewHolder(inflate);
//            补贴项目
            case VIEW_TYPE_SUBSIDY_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_subsidy, parent, false);
                return new SubsidyProjectViewHolder(inflate);
//              扣费项目
            case VIEW_TYPE_DEDUCT_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_deduct_project, parent, false);
                return new DeductProjectViewHolder(inflate);
//              代缴费项目
            case VIEW_TYPE_AGENCY_PROJECT:
                inflate = layoutInflater.inflate(R.layout.item_statistics_agency_project, parent, false);
                return new AgencyProjectViewHolder(inflate);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
//                总收入
                return VIEW_TYPE_ALL_INCOMING;
            case 1:
//                基本项目
                return VIEW_TYPE_BASIC_PROJECT;
            case 2:
//                补贴项目
                return VIEW_TYPE_SUBSIDY_PROJECT;
            case 3:
//                扣款项目
                return VIEW_TYPE_DEDUCT_PROJECT;
            case 4:
//                代缴费项目
                return VIEW_TYPE_AGENCY_PROJECT;
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    /**
     * 总收入
     */
    static class AllIncomingViewHolder extends RecyclerView.ViewHolder {
        public AllIncomingViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 基本项目
     */
    static class BasicProjectViewHolder extends RecyclerView.ViewHolder {
        public BasicProjectViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 补贴项目
     */
    static class SubsidyProjectViewHolder extends RecyclerView.ViewHolder {
        public SubsidyProjectViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 扣款项目
     */
    static class DeductProjectViewHolder extends RecyclerView.ViewHolder {
        public DeductProjectViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 扣款项目
     */
    static class AgencyProjectViewHolder extends RecyclerView.ViewHolder {
        public AgencyProjectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
