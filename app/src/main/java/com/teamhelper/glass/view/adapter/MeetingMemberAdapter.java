package com.teamhelper.glass.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ItemMeetingMemberBinding;
import com.teamhelper.glass.interfaces.IOnItemClickListener;
import com.teamhelper.glass.view.holder.BaseViewHolder;
import com.teamhelper.meeting.bean.ContactsBean;

import java.util.List;

public class MeetingMemberAdapter extends BaseRecyclerAdapter<BaseViewHolder> {
    private final Context context;
    private final List<ContactsBean> dataList;
    private IOnItemClickListener<ContactsBean> onItemClickListener;

    public MeetingMemberAdapter(Context context, List<ContactsBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(IOnItemClickListener<ContactsBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMeetingMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_meeting_member, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemMeetingMemberBinding binding = (ItemMeetingMemberBinding) holder.getDataBinding();
        binding.setData(dataList.get(position));
        binding.getRoot().setContentDescription(String.valueOf(2 + position + 1));
        binding.getRoot().setOnClickListener(view -> {
            if (onItemClickListener == null) return;
            onItemClickListener.onItemClick(view, position, dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
