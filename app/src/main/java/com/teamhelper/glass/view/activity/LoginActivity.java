package com.teamhelper.glass.view.activity;

import com.mst.basics.base.view.activity.GlassBaseActivity;
import com.teamhelper.base.mvvm.databinding.viewmodel.EmptyViewModel;
import com.teamhelper.glass.Config;
import com.teamhelper.glass.constants.Instruct;
import com.teamhelper.glass.databinding.ActivityLoginBinding;
import com.teamhelper.glass.manager.IntentManager;
import com.teamhelper.glass.utils.StringUtil;
import com.teamhelper.glass.utils.ToastUtil;
import com.teamhelper.meeting.bean.UserBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.tools.ActivityStackManager;

public class LoginActivity extends GlassBaseActivity<ActivityLoginBinding, EmptyViewModel> {
    public static UserBean userBean;

    @Override
    public void initData() {

    }

    @Override
    public void initParams() {

    }

    @Override
    public void initView() {
        instructManager.addInstruct(Instruct.INPUT_0, v.tv0);
        v.tv0.setOnClickListener(view -> {
            v.tvUserId.append("0");
        });
        instructManager.addInstruct(Instruct.INPUT_1, v.tv1);
        v.tv1.setOnClickListener(view -> {
            v.tvUserId.append("1");
        });
        instructManager.addInstruct(Instruct.INPUT_2, v.tv2);
        v.tv2.setOnClickListener(view -> {
            v.tvUserId.append("2");
        });
        instructManager.addInstruct(Instruct.INPUT_3, v.tv3);
        v.tv3.setOnClickListener(view -> {
            v.tvUserId.append("3");
        });
        instructManager.addInstruct(Instruct.INPUT_4, v.tv4);
        v.tv4.setOnClickListener(view -> {
            v.tvUserId.append("4");
        });
        instructManager.addInstruct(Instruct.INPUT_5, v.tv5);
        v.tv5.setOnClickListener(view -> {
            v.tvUserId.append("5");
        });
        instructManager.addInstruct(Instruct.INPUT_6, v.tv6);
        v.tv6.setOnClickListener(view -> {
            v.tvUserId.append("6");
        });
        instructManager.addInstruct(Instruct.INPUT_7, v.tv7);
        v.tv7.setOnClickListener(view -> {
            v.tvUserId.append("7");
        });
        instructManager.addInstruct(Instruct.INPUT_8, v.tv8);
        v.tv8.setOnClickListener(view -> {
            v.tvUserId.append("8");
        });
        instructManager.addInstruct(Instruct.INPUT_9, v.tv9);
        v.tv9.setOnClickListener(view -> {
            v.tvUserId.append("9");
        });
        instructManager.addInstruct(Instruct.LOGIN, v.tvLogin);
        v.tvLogin.setOnClickListener(view -> {
            String userId = v.tvUserId.getText().toString();
            if (StringUtil.isEmpty(userId)) {
                ToastUtil.showToast(getMContext(), "请输入用户ID");
                return;
            }
            if (StringUtil.isEmpty(Config.APP_KEY)) {
                ToastUtil.showToast(getMContext(), "请配置appKey");
                return;
            }
            if (StringUtil.isEmpty(Config.ACCESS_KEY)) {
                ToastUtil.showToast(getMContext(), "请配置accessKey");
                return;
            }
            if (StringUtil.isEmpty(Config.ACCESS_SECRET)) {
                ToastUtil.showToast(getMContext(), "请配置accessSecret");
                return;
            }
            showLoading("Loading...");
            //5个小时后token到期
            long timestamp = System.currentTimeMillis() + (5 * 60 * 60 * 1000);
            MeetingManager.exampleLogin(userId, Config.APP_KEY, Config.ACCESS_KEY, Config.ACCESS_SECRET, timestamp, new IMeetingCallback<UserBean>() {
                @Override
                public void onSuccess(UserBean data) {
                    dismissLoading();
                    userBean = data;
                    IntentManager.build(MeetingTodayActivity.class).startActivity(getMContext());
                    finish();
                }

                @Override
                public void onError(int code, String message) {
                    dismissLoading();
                    ToastUtil.showToast(getMContext(), message);
                }
            });
        });
        v.instructMenu.setInstructManager(instructManager);
        v.instructMenu.addInstruct(Instruct.UNDO, view -> {
            String value = v.tvUserId.getText().toString();
            if (StringUtil.isEmpty(value)) return;
            if (value.length() == 1) {
                value = "";
            } else {
                value = value.substring(0, value.length() - 1);
            }
            v.tvUserId.setText(value);
        });
        v.instructMenu.addInstruct(Instruct.DELETE_CONTENT, view -> {
            v.tvUserId.setText("");
        });
        v.instructMenu.addInstruct(Instruct.EXIT, view -> {
            ActivityStackManager.getInstance().finishAllActivity();
        });
        slideEventViewManager.setCheckViewListener(v.tv0);
    }

    @Override
    public void registerObserve() {

    }

    @Override
    public void onBack() {
        super.onBack();
        ActivityStackManager.getInstance().finishAllActivity();
    }
}
