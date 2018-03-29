package com.foxconn.beacon.salary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.foxconn.beacon.salary.R;
import com.foxconn.beacon.salary.adapter.RecordOvertimeAdapter;
import com.foxconn.beacon.salary.base.BaseActivity;
import com.foxconn.beacon.salary.constants.MyConstants;
import com.foxconn.beacon.salary.model.DBOperatorHelper;
import com.foxconn.beacon.salary.model.DayWorkInfo;
import com.foxconn.beacon.salary.model.SalaryOperationHelper;
import com.foxconn.beacon.salary.utils.DateUtils;
import com.foxconn.beacon.salary.utils.ToastUtils;
import com.foxconn.beacon.salary.utils.UIUtils;
import com.foxconn.beacon.salary.view.WheelView;
import com.beacon.materialcalendar.CalendarDay;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * @author: F1331886
 * @date: 2017/10/30 0030.
 * @describe: 记加班界面
 */

public class RecordOvertimeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, RecordOvertimeAdapter.OnItemClickListener {
    @BindView(R.id.fl_commend_content)
    LinearLayout mFrameLayout;

    @BindView(R.id.btn_record_save)
    Button mBtnRecordSave;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_toolbar_subtitle)
    TextView mTvToolbarSubtitle;
    @BindView(R.id.btn_toolbar_right)
    Button mBtnToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RecyclerView mRecyclerRecord;
    private RecordOvertimeAdapter mAdapter;
    private PopupWindow mPopupWindow;

    private boolean isWheelWindow = false;
    private ListView mLvPopupData;
    private View mWheelPopupView;
    private View mListPopupView;

    private List<String> mPopupItems;
    private int mCurrentClickItem;
    private WheelView mWheelWindowMinutes;
    private WheelView mWheelWindowHours;
    private StringBuilder mStringBuilder = new StringBuilder();
    private TextView mTvListTitle;
    private TextView mTvWheelTitle;
    private CalendarDay mCalendarDay;
    /**
     * 加班类别
     */
    private float mOvertimeType = 1.5f;
    /**
     * 出勤类别的索引
     */
    private int mWorkType;
    /**
     * 请假类别的索引
     */
    private int mLeaveTypePosition;
    private long mSelectDate;
    private DayWorkInfo mDayWorkInfo;
    private RadioGroup mRadioGroupTop;

    @Override
    protected void initContentView() {
        initToolbar();
        LayoutInflater from = LayoutInflater.from(this);
        View textView = from.inflate(R.layout.textview_record_overtime_title, null);
        mFrameLayout.addView(textView);
        View radio = from.inflate(R.layout.radio_group_record_overtime, null);
        mRadioGroupTop = radio.findViewById(R.id.radio_group_record);
        mFrameLayout.addView(radio);
        mRecyclerRecord = new RecyclerView(this);
        mRecyclerRecord.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerRecord.setBackgroundColor(Color.WHITE);
        mFrameLayout.addView(mRecyclerRecord);

        initPopupWindow();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mCalendarDay = CalendarDay.from(Calendar.getInstance());
        boolean isFromCalendar = getIntent().getBooleanExtra(MyConstants.IS_FROM_CALENDAR, false);
        //        获取日期
        mSelectDate = getIntent().getLongExtra(MyConstants.SELECT_DATE, mCalendarDay.getDate().getTime());
        mDayWorkInfo = (DayWorkInfo) getIntent().getSerializableExtra(MyConstants.SELECT_DAY_INFO);

        chooseShowDelete();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerRecord.setLayoutManager(layoutManager);
        mAdapter = new RecordOvertimeAdapter(mDayWorkInfo, mSelectDate);
        mRecyclerRecord.setAdapter(mAdapter);

        if (isFromCalendar) {
            mFrameLayout.getChildAt(1).setVisibility(View.GONE);
        } else {
            mFrameLayout.getChildAt(1).setVisibility(View.VISIBLE);
            mRadioGroupTop.setOnCheckedChangeListener(this);
            mRadioGroupTop.check(R.id.radio_button_today);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        Date date = new Date(mSelectDate);
        switch (checkedId) {
//            今天
            case R.id.radio_button_today:
                break;
//           前天
            case R.id.radio_button_before_yest:
                date = DateUtils.nextDay(date, -2);
                break;
//           昨天
            case R.id.radio_button_yest:
                date = DateUtils.nextDay(date, -1);
                break;
//          其他
            default:
                Intent intent = new Intent();
                setResult(MyConstants.RESULT_RECORD_OVERTIME_TO_CALENDAR, intent);
                finish();
                return;
        }
        mDayWorkInfo = DBOperatorHelper.getDayOvertimeInfo(date.getTime());
        chooseShowDelete();
        mAdapter.refresh(mDayWorkInfo, date);
    }

    /**
     * 是否显示删除按钮
     */
    private void chooseShowDelete() {
        if (mDayWorkInfo != null) {
            mBtnToolbarRight.setVisibility(View.VISIBLE);
            mBtnToolbarRight.setOnClickListener(this);
        } else {
            mBtnToolbarRight.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化Toolbar信息
     */
    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mTvToolbarSubtitle.setVisibility(View.GONE);
        mTvToolbarTitle.setText("记加班");
    }

    /**
     * 初始化事件监听
     */
    @Override
    protected void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnRecordSave.setOnClickListener(this);
//        弹出窗口的每个Item的点击事件
        mLvPopupData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mCurrentClickItem) {
//                        出勤班别
                    case R.id.statisticsItem_work_type:
                        mWorkType = position;
                        mAdapter.setWorkType(mPopupItems.get(position));
                        break;
//                        加班类型
                    case R.id.statisticsItem_overtime_type:
                        if (position == 0) {
                            mOvertimeType = 1.5f;
                        } else if (position == 1) {
                            mOvertimeType = 2.0f;
                        } else {
                            mOvertimeType = 3.0f;
                        }
                        mAdapter.setOvertimeType(mPopupItems.get(position));
                        break;
//                        请假类型
                    case R.id.statisticsItem_leave_type:
                        mLeaveTypePosition = position;
                        mAdapter.setLeaveType(mPopupItems.get(position));
                        break;
                    default:
                        break;
                }
//                取消显示Dialog
                dismissPopupWindow();
            }
        });
        mAdapter.setOnItemClickListener(this);
    }

    /**
     * 解析时间格式  8小时0分钟 ----》8小时，0分钟
     *
     * @param workDuration
     * @return
     */
    private List<String> getItemsFromDate(String workDuration) {
        int hour = workDuration.indexOf("小时");
        mPopupItems = new ArrayList<>();
        mPopupItems.add(workDuration.substring(0, hour + 2));
        mPopupItems.add(workDuration.substring(hour + 2, workDuration.length()));
        return mPopupItems;
    }

    /**
     * 加班类型数据
     */
    private List<String> overtimeTypeData() {
        mStringBuilder.delete(0, mStringBuilder.length());
        mPopupItems = new ArrayList<>();
        mPopupItems.add(mStringBuilder.append(SalaryOperationHelper.getWorkDateOvertimeSalary(this,
                DateUtils.getYear(new Date(mSelectDate)),
                DateUtils.getMonth(new Date(mSelectDate))))
                .append("元/小时(工作日1.5倍)").toString());
        mStringBuilder.delete(0, mStringBuilder.length());
        mPopupItems.add(mStringBuilder.append(SalaryOperationHelper.getRestDateOvertimeSalary(this,
                DateUtils.getYear(new Date(mSelectDate)),
                DateUtils.getMonth(new Date(mSelectDate))))
                .append("元/小时(休息日2.0倍)").toString());
        mStringBuilder.delete(0, mStringBuilder.length());
        mPopupItems.add(mStringBuilder.append(SalaryOperationHelper.getHolidayDateOvertimeSalary(this,
                DateUtils.getYear(new Date(mSelectDate)),
                DateUtils.getMonth(new Date(mSelectDate))))
                .append("元/小时(节假日3.0倍)").toString());
        mStringBuilder.delete(0, mStringBuilder.length());
        return mPopupItems;
    }

    @Override
    protected void processClickEvent(View view) {
        switch (view.getId()) {
//            删除
            case R.id.btn_toolbar_right:
                int i = DataSupport.deleteAll(DayWorkInfo.class, "datetime=?", String.valueOf(mSelectDate));
                if (i >= 0) {
                    showToast("删除成功");
                }
                finish();
                break;
//            保存加班信息
            case R.id.btn_record_save:
                //        先删除这一天的加班数据 在进行保存
                DataSupport.deleteAll(DayWorkInfo.class, "datetime=?", String.valueOf(mSelectDate));
                mCalendarDay = CalendarDay.from(new Date(mSelectDate));

//                每日加班的基本数据
                DayWorkInfo dayWorkInfo = new DayWorkInfo();
                dayWorkInfo.setYear(mCalendarDay.getYear());
                dayWorkInfo.setMonth(mCalendarDay.getMonth());
                dayWorkInfo.setDay(mCalendarDay.getDay());
                dayWorkInfo.setDatetime(mSelectDate);
                dayWorkInfo.setWorkTime(getHoursFromText(mAdapter.getWorkDuration()));
                dayWorkInfo.setWorkType(mWorkType);
//                      请假数据
                dayWorkInfo.setLeaveDuration(getHoursFromText(mAdapter.getLeaveDuration()));
                dayWorkInfo.setLeaveType(mLeaveTypePosition);
                dayWorkInfo.setOvertimeType(mOvertimeType);
                dayWorkInfo.setOvertimeDuration(getHoursFromText(mAdapter.getOvertimeDuration()));
                dayWorkInfo.setRemark(mAdapter.getRemark());
//                加班数据
                if (dayWorkInfo.save()) {
                    ToastUtils.showShort(this, "保存成功");
                    finish();
                }
                break;
//            popupWindow的确定按钮
            case R.id.tv_popup_wheel_ok:
                mStringBuilder = new StringBuilder();
                mStringBuilder.append(TextUtils.isEmpty(mWheelWindowHours.getSeletedItem()) ? "0小时" : mWheelWindowHours.getSeletedItem());
                mStringBuilder.append(TextUtils.isEmpty(mWheelWindowMinutes.getSeletedItem()) ? "0分钟" : mWheelWindowMinutes.getSeletedItem());
                if (mCurrentClickItem == R.id.statisticsItem_leave_duration) {
                    mAdapter.setLeaveDuration(mStringBuilder.toString());
                } else {
                    mAdapter.setWorkDuration(mStringBuilder.toString());
                }
            case R.id.tv_popup_wheel_cancel:
            case R.id.tv_popup_list_cancel:
                dismissPopupWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 解析一个字符串变成小时
     *
     * @param text
     * @return
     */
    private float getHoursFromText(String text) {
        String[] hours = text.split("小时");
        String[] minutes = hours[1].split("分钟");
        return Float.valueOf(hours[0]) + Float.valueOf(new DecimalFormat("0.00").format(Float.valueOf(minutes[0]) / 60));
    }

    /**
     * 选择背景图的窗口
     */
    private void chooseWindow(List<String> items, String title) {
        if (isWheelWindow) {
            mTvWheelTitle.setText(title);
            List<String> hours = Arrays.asList(UIUtils.getArrays(R.array.work_hours));
            List<String> minutes = Arrays.asList(UIUtils.getArrays(R.array.work_minutes));

            mWheelWindowHours.setItems(hours);
            mWheelWindowHours.setSeletion(hours.indexOf(items.get(0)));
            mWheelWindowMinutes.setItems(minutes);
            mWheelWindowMinutes.setSeletion(minutes.indexOf(items.get(1)));

            showWindow(mWheelPopupView);

        } else {
            mTvListTitle.setText(title);
            BaseAdapter adapter;
            if (title.contains("班别")) {
                adapter = new SimpleAdapter(RecordOvertimeActivity.this, getWorkTypeList(), R.layout.item_image_textview,
                        new String[]{"text", "image"}, new int[]{R.id.tv_image_text, R.id.iv_image});
            } else {
                adapter = new ArrayAdapter(this, R.layout.textview, R.id.tv_popup_item, items);
            }
            mLvPopupData.setAdapter(adapter);
            showWindow(mListPopupView);
        }
    }

    /**
     * 获取 出勤类别的种类
     *
     * @return
     */
    private List<Map<String, Object>> getWorkTypeList() {
        List<Map<String, Object>> list = new ArrayList();
        int[] icons = new int[]{R.drawable.ic_day_shift, R.drawable.ic_middle_shift, R.drawable.ic_night_shift, R.drawable.ic_rest_day};
        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("text", mPopupItems.get(i));
            map.put("image", icons[i]);
            list.add(map);
        }
        return list;
    }

    /**
     * 显示窗口
     *
     * @param contentView
     */
    private void showWindow(View contentView) {
        if (!mPopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mPopupWindow.setContentView(contentView);
            lp.alpha = 0.4f;
            getWindow().setAttributes(lp);
            mPopupWindow.showAtLocation(mRecyclerRecord, Gravity.BOTTOM, 0, 0);
        } else {
            dismissPopupWindow();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            dismissPopupWindow();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 取消显示对话框
     */
    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            lp.alpha = 1f;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * 初始化窗口
     */
    private void initPopupWindow() {

        mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //使用默認的動畫樣式
        mPopupWindow.setAnimationStyle(R.style.animTranalate);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1f;
                window.setAttributes(attributes);
            }
        });
//       时间选择Dialog视图
        mWheelPopupView = View.inflate(this, R.layout.popup_choose_wheel, null);
        TextView tvPopupOk = mWheelPopupView.findViewById(R.id.tv_popup_wheel_ok);
        mTvWheelTitle = mWheelPopupView.findViewById(R.id.tv_popup_wheel_title);
        mWheelWindowMinutes = mWheelPopupView.findViewById(R.id.wheel_popup_minutes);
        mWheelWindowHours = mWheelPopupView.findViewById(R.id.wheel_popup_hour);
        TextView tvWheelPopupCancel = mWheelPopupView.findViewById(R.id.tv_popup_wheel_cancel);

//        列表选择Dialog视图
        mListPopupView = View.inflate(this, R.layout.popup_choose_list, null);
        mTvListTitle = mListPopupView.findViewById(R.id.tv_popup_list_title);
        mLvPopupData = mListPopupView.findViewById(R.id.lv_popup_list_data);
        TextView tvListPopupCancel = mListPopupView.findViewById(R.id.tv_popup_list_cancel);

        tvPopupOk.setOnClickListener(this);
        tvWheelPopupCancel.setOnClickListener(this);
        tvListPopupCancel.setOnClickListener(this);
    }
    @Override
    protected int getResId() {
        return R.layout.activity_commend;
    }

    @Override
    public void onItemClick(View view) {
        switch (view.getId()) {
//                     出勤类型
            case R.id.statisticsItem_work_type:
                isWheelWindow = false;
                mPopupItems = Arrays.asList(UIUtils.getArrays(R.array.work_type));
                chooseWindow(mPopupItems, "请选择出勤班别");
                break;
//                     出勤时间
            case R.id.statisticsItem_work_time:
                isWheelWindow = true;
                chooseWindow(getItemsFromDate(mAdapter.getWorkDuration()), "请选择应出勤时间");
                break;
//                    加班类别
            case R.id.statisticsItem_overtime_type:
                isWheelWindow = false;
                chooseWindow(overtimeTypeData(), "请选择工资标准");
                break;
            case R.id.statisticsItem_leave_type:
                isWheelWindow = false;
                mPopupItems = Arrays.asList(UIUtils.getArrays(R.array.leave_type));
                chooseWindow(mPopupItems, "请选择请假类型");
                break;
            case R.id.statisticsItem_leave_duration:
                isWheelWindow = true;
                chooseWindow(getItemsFromDate(mAdapter.getLeaveDuration()), "请选择请假时长");
            default:
                break;
        }
        mCurrentClickItem = view.getId();
    }

}
