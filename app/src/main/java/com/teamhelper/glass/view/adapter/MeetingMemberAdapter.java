package com.teamhelper.glass.view.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mst.basics.BR;
import com.mst.basics.databinding.EmptyBinding;
import com.mst.basics.slide.widget.LinearLayoutCompatWidget;
import com.teamhelper.base.mvvm.databinding.view.adapter.BaseRecyclerViewAdapter;
import com.teamhelper.glass.databinding.ItemMeetingMemberBinding;
import com.teamhelper.meeting.bean.ContactsBean;

import java.util.List;

public class MeetingMemberAdapter extends BaseRecyclerViewAdapter<ContactsBean, ItemMeetingMemberBinding, EmptyBinding> {

    public MeetingMemberAdapter(Context context, List<ContactsBean> dataList) {
        super(context, dataList);
    }

    @Override
    public int getBR() {
        return BR._data;
    }

    @Override
    public void onBindNonEmptyViewHolder(@NonNull ItemMeetingMemberBinding binding, ContactsBean contactsBean, int position) {
        binding.setData(contactsBean);
        LinearLayoutCompatWidget root = (LinearLayoutCompatWidget) binding.getRoot();
        root.setContentDescription(String.valueOf(2 + position + 1));
        root.setOnClickListener(view -> {
            if (onItemClickListener == null) return;
            onItemClickListener.onItemClick(view, position, dataList.get(position));
        });
    }
}
