package com.teamhelper.glass.view.activity;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.teamhelper.base.view.activity.RootActivity;
import com.teamhelper.base.view.widget.LinearLayoutCompatWidget;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ActivityMeetingMemberBinding;
import com.teamhelper.glass.enums.InstructSingle;
import com.teamhelper.glass.utils.StringUtil;
import com.teamhelper.glass.view.adapter.MeetingMemberAdapter;
import com.teamhelper.meeting.bean.ContactsBean;
import com.teamhelper.meeting.manager.MeetingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingMemberActivity extends RootActivity<ActivityMeetingMemberBinding> {
    private final List<ContactsBean> dataList = new ArrayList<>();
    private MeetingMemberAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_meeting_member;
    }

    @Override
    public void onCreate() {
        dataList.addAll(MeetingManager.getContactsList());
        dataList.forEach(item -> item.setCheckStatus(ContactsBean.UNSELECT));
        dataList.stream().filter(t -> StringUtil.equals(t.getUserId(), MeetingManager.getUserBean().getUserId())).forEach(bean -> bean.setCheckStatus(ContactsBean.DISABLE));
        instructManager.addInstruct(InstructSingle.BACK, dataBinding.tvBack);
        dataBinding.tvBack.setText(InstructSingle.BACK.getInstruct());
        dataBinding.tvBack.setOnClickListener(view -> finish());
        dataBinding.tvTitle.setText("添加参与者");
        instructManager.addInstruct(InstructSingle.CONFIRM, dataBinding.tvConfirm);
        dataBinding.tvConfirm.setText(InstructSingle.CONFIRM.getInstruct());
        dataBinding.tvConfirm.setOnClickListener(view -> {
            List<String> collect = dataList.stream().filter(item -> item.getCheckStatus() == ContactsBean.SELECTED).map(ContactsBean::getUserId).collect(Collectors.toList());
            Intent intent = new Intent();
            intent.putExtra("inviteeUserIds", new ArrayList<>(collect));
            setResult(RESULT_OK, intent);
            finish();
        });
        slideEventViewManager.setCheckViewListener(dataBinding.tvBack);
        adapter = new MeetingMemberAdapter(activity, dataList);
        adapter.setOnItemClickListener((v, position, itemData) -> {
            if (itemData.getCheckStatus() == ContactsBean.DISABLE) return;
            if (itemData.getCheckStatus() == ContactsBean.SELECTED) {
                itemData.setCheckStatus(ContactsBean.UNSELECT);
            } else {
                itemData.setCheckStatus(ContactsBean.SELECTED);
            }
            adapter.refresh();
            dataBinding.recyclerView.onRenderComplete(unused -> {
                slideEventViewManager.setCheckViewListener((LinearLayoutCompatWidget) v);
            });
        });
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        dataBinding.recyclerView.setAdapter(adapter);
        dataBinding.recyclerView.onRenderComplete(unused -> {
            LinearLayoutCompatWidget llContent = (LinearLayoutCompatWidget) dataBinding.recyclerView.getLayoutManager().findViewByPosition(0);
            slideEventViewManager.setCheckViewListener(llContent);
        });
    }


}
