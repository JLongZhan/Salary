package com.foxconn.beacon.salary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseApplication;
import com.foxconn.beacon.salary.view.WheelView;

import java.util.Arrays;

/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe:
 */

public class RecordOvertimeAdapter extends RecyclerView.Adapter {
    /**
     *    工作日期
      */
    private static final int VIEW_TYPE_WORKDATE = 0;
    //    加班详情
    private static final int VIEWTYPE_OVERTIME_DETAIL = 1;
    //    请假详情
    private static final int VIEWTYPE_LEAVE_DETAIL = 2;
    //    备注
    private static final int VIEWTYPE_REMARK = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_WORKDATE:
                inflate = layoutInflater.inflate(R.layout.item_record_workdate, parent, false);
                return new WorkDateViewHolder(inflate);
            case VIEWTYPE_OVERTIME_DETAIL:
                inflate = layoutInflater.inflate(R.layout.item_record_overtime_details, parent, false);
                return new OverTimeDetailsViewHolder(inflate);
            case VIEWTYPE_LEAVE_DETAIL:
                inflate = layoutInflater.inflate(R.layout.item_record_leavetype, parent, false);
                return new LeaveDetailsViewHolder(inflate);
            case VIEWTYPE_REMARK:
                inflate = layoutInflater.inflate(R.layout.item_record_remark, parent, false);
                return new RemarkViewHolder(inflate);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_WORKDATE:

                break;
            case VIEWTYPE_OVERTIME_DETAIL:
                OverTimeDetailsViewHolder holder1 = (OverTimeDetailsViewHolder) holder;
                holder1.wheelViewHours.setItems(Arrays.asList(BaseApplication.sContext.getResources().getStringArray(R.array.work_hours)));
                holder1.wheelViewMinutes.setItems(Arrays.asList(BaseApplication.sContext.getResources().getStringArray(R.array.work_minutes)));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return VIEW_TYPE_WORKDATE;
            case 1:
                return VIEWTYPE_OVERTIME_DETAIL;
            case 2:
                return VIEWTYPE_LEAVE_DETAIL;
            case 3:
                return VIEWTYPE_REMARK;
            default:
                break;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    static class WorkDateViewHolder extends RecyclerView.ViewHolder {
        public WorkDateViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class OverTimeDetailsViewHolder extends RecyclerView.ViewHolder {
        WheelView wheelViewHours;
        WheelView wheelViewMinutes;

        public OverTimeDetailsViewHolder(View itemView) {
            super(itemView);
            wheelViewHours = itemView.findViewById(R.id.wheel_record_hours);
            wheelViewMinutes = itemView.findViewById(R.id.wheel_record_minutes);
        }
    }

    static class LeaveDetailsViewHolder extends RecyclerView.ViewHolder {
        public LeaveDetailsViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class RemarkViewHolder extends RecyclerView.ViewHolder {
        public RemarkViewHolder(View itemView) {
            super(itemView);
        }
    }
}
