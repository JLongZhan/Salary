package com.foxconn.beacon.salary.fragment.statistics;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.base.BaseSettingFragment;

import butterknife.BindView;

/**
 * @author: F1331886
 * @date: 2017/11/6 0006.
 * @describe: 扣款、补贴
 */

public class DeductProjectFragment extends BaseSettingFragment {
    private static final String TAG = "DeductProjectFragment";
    @BindView(R.id.lv_deduct_project)
    ListView mLvDeductProject;
    private CharSequence[] mCharSequenceArray;
    private String[] mItemCheckedArray;
    private boolean mIsDeduct;

    @Override
    protected int getContentResId() {
        return R.layout.listview;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        mCharSequenceArray = arguments.getCharSequenceArray(PARAM1);
        mIsDeduct = arguments.getBoolean(PARAM2);
//        判断是补贴还是扣除项目
        String itemChecked;
        if (mIsDeduct) {
            itemChecked = mMonthSetting.getDeductItemChecked();
        } else {
            itemChecked = mMonthSetting.getSubsidyItemChecked();
        }
        if (TextUtils.isEmpty(itemChecked)) {
            mItemCheckedArray = new String[mIsDeduct ? 6 : 12];
        } else {
            mItemCheckedArray = itemChecked.split("@");
        }
        mLvDeductProject.setAdapter(new MyAdapter());
    }

    /**
     * 开启一个Fragment
     *
     * @param object
     * @return
     */
    public static DeductProjectFragment startFragment(Object object, boolean isDeduct) {
        DeductProjectFragment fragment = new DeductProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequenceArray(PARAM1, (CharSequence[]) object);
        bundle.putBoolean(PARAM2, isDeduct);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 数据适配器
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mCharSequenceArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_deduct_project, null);
            }
            viewHolder = getViewHolder(convertView);
            viewHolder.mTextView.setText(mCharSequenceArray[position]);
            viewHolder.mCheckBox.setChecked(Boolean.parseBoolean(mItemCheckedArray[position]));
            viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.mCheckBox.setChecked(!viewHolder.mCheckBox.isChecked());
                    mItemCheckedArray[position] = Boolean.toString(viewHolder.mCheckBox.isChecked());
                }
            });
            return convertView;
        }
    }

    /**
     * 获取ViewHolder
     *
     * @param view
     * @return
     */
    private ViewHolder getViewHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    /**
     * 减少findViewById的使用
     */
    class ViewHolder {
        CheckBox mCheckBox;
        TextView mTextView;
        LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            mLinearLayout = view.findViewById(R.id.ll_deduct_project_item);
            mCheckBox = view.findViewById(R.id.cb_deduct_project);
            mTextView = view.findViewById(R.id.tv_deduct_project);
        }
    }

    /**
     * 保存数据
     */
    @Override
    public boolean save() {
        StringBuilder sb = new StringBuilder();
        for (String b : mItemCheckedArray) {
            sb.append(TextUtils.isEmpty(b) ? "false" : b).append("@");
        }
        if (mIsDeduct) {
            mMonthSetting.setDeductItemChecked(sb.toString());
        } else {
            mMonthSetting.setSubsidyItemChecked(sb.toString());
        }
        return true;
    }
}
