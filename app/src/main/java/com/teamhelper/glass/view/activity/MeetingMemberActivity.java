package com.teamhelper.glass.view.activity;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.mst.basics.base.view.activity.GlassBaseActivity;
import com.mst.basics.slide.widget.LinearLayoutCompatWidget;
import com.teamhelper.base.mvvm.databinding.view.adapter.BaseRecyclerViewAdapter;
import com.teamhelper.base.mvvm.databinding.viewmodel.EmptyViewModel;
import com.teamhelper.glass.constants.Instruct;
import com.teamhelper.glass.databinding.ActivityMeetingMemberBinding;
import com.teamhelper.glass.utils.StringUtil;
import com.teamhelper.glass.view.adapter.MeetingMemberAdapter;
import com.teamhelper.meeting.bean.ContactsBean;
import com.teamhelper.meeting.manager.MeetingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingMemberActivity extends GlassBaseActivity<ActivityMeetingMemberBinding, EmptyViewModel> {
    private final List<ContactsBean> dataList = new ArrayList<>();
    private MeetingMemberAdapter adapter;

    @Override
    public void initData() {

    }

    @Override
    public void initParams() {

    }

    @Override
    public void initView() {
        dataList.addAll(MeetingManager.getContactsList());
        dataList.forEach(item -> item.setCheckStatus(ContactsBean.UNSELECT));
        dataList.stream().filter(t -> StringUtil.equals(t.getUserId(), MeetingManager.getUserBean().getUserId())).forEach(bean -> bean.setCheckStatus(ContactsBean.DISABLE));
        instructManager.addInstruct(Instruct.BACK, v.tvBack);
        v.tvBack.setText(Instruct.BACK.getInstruct());
        v.tvBack.setOnClickListener(view -> finish());
        v.tvTitle.setText("添加参与者");
        instructManager.addInstruct(Instruct.CONFIRM, v.tvConfirm);
        v.tvConfirm.setText(Instruct.CONFIRM.getInstruct());
        v.tvConfirm.setOnClickListener(view -> {
            List<String> collect = dataList.stream().filter(item -> item.getCheckStatus() == ContactsBean.SELECTED).map(ContactsBean::getUserId).collect(Collectors.toList());
            Intent intent = new Intent();
            intent.putExtra("inviteeUserIds", new ArrayList<>(collect));
            setResult(RESULT_OK, intent);
            finish();
        });
        adapter = new MeetingMemberAdapter(getMContext(), dataList);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ContactsBean>() {
            @Override
            public void onItemClick(View view, int i, ContactsBean contactsBean) {
                super.onItemClick(view, i, contactsBean);
                if (contactsBean.getCheckStatus() == ContactsBean.DISABLE) return;
                if (contactsBean.getCheckStatus() == ContactsBean.SELECTED) {
                    contactsBean.setCheckStatus(ContactsBean.UNSELECT);
                } else {
                    contactsBean.setCheckStatus(ContactsBean.SELECTED);
                }
                adapter.refreshData();
                v.recyclerView.onRenderComplete(unused -> {
                    slideEventViewManager.setCheckViewListener((LinearLayoutCompatWidget) view);
                });
            }
        });
        v.recyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        v.recyclerView.setAdapter(adapter);
        if (dataList.size() == 0) {
            slideEventViewManager.setCheckViewListener(v.tvBack);
            return;
        }
        v.recyclerView.onRenderComplete(unused -> {
            LinearLayoutCompatWidget llContent = (LinearLayoutCompatWidget) v.recyclerView.getLayoutManager().findViewByPosition(0);
            slideEventViewManager.setCheckViewListener(llContent);
        });
    }

    @Override
    public void registerObserve() {

    }
}
