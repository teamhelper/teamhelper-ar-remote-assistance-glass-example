package com.teamhelper.glass.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.teamhelper.base.interfaces.ISlideEventViewStateListener;
import com.teamhelper.base.view.widget.FrameLayoutWidget;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ItemMeetingTodayBinding;
import com.teamhelper.glass.interfaces.IOnItemClickListener;
import com.teamhelper.glass.view.holder.BaseViewHolder;
import com.teamhelper.meeting.bean.meeting.MeetingBean;

import java.util.List;

public class MeetingTodayAdapter extends BaseRecyclerAdapter<BaseViewHolder> {
    private final Context context;
    private final List<MeetingBean> dataList;
    private IOnItemClickListener<MeetingBean> onItemClickListener;

    public MeetingTodayAdapter(Context context, List<MeetingBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(IOnItemClickListener<MeetingBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMeetingTodayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_meeting_today, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemMeetingTodayBinding binding = (ItemMeetingTodayBinding) holder.getDataBinding();
        MeetingBean item = dataList.get(position);
        binding.tvMeetingName.setText(item.getMeetingName());
        binding.tvTime.setText(item.getTimeStr());
        binding.tvMasterName.setText(context.getString(R.string.text_initiator) + item.getMasterName());
        binding.tvMeetingNo.setText(context.getString(R.string.text_meeting_no) + item.getMeetingNo());
        FrameLayoutWidget root = (FrameLayoutWidget) binding.getRoot();
        root.setContentDescription(String.valueOf(position + 1 + 2));
        root.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onItemClick(v, position, item);
        });
        root.setSlideEventViewStateListener(new ISlideEventViewStateListener() {
            @Override
            public void onUncheck(View v) {
                super.onUncheck(v);
                binding.tvMeetingName.setTextColor(context.getColor(R.color.color_ffffff));
                binding.ivTime.setImageResource(R.mipmap.ic_meeting_time_uncheck);
                binding.tvTime.setTextColor(context.getColor(R.color.color_ffffff));
                binding.tvMasterName.setTextColor(context.getColor(R.color.color_ffffff));
                binding.tvMeetingNo.setTextColor(context.getColor(R.color.color_ffffff));
            }

            @Override
            public void onChecked(View v) {
                super.onChecked(v);
                binding.tvMeetingName.setTextColor(context.getColor(R.color.color_000000));
                binding.ivTime.setImageResource(R.mipmap.ic_meeting_time_checked);
                binding.tvTime.setTextColor(context.getColor(R.color.color_000000));
                binding.tvMasterName.setTextColor(context.getColor(R.color.color_000000));
                binding.tvMeetingNo.setTextColor(context.getColor(R.color.color_000000));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
