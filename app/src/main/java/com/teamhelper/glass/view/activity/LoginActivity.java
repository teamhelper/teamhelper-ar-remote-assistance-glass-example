package com.teamhelper.glass.view.activity;

import com.teamhelper.base.view.activity.RootActivity;
import com.teamhelper.glass.BuildConfig;
import com.teamhelper.glass.Config;
import com.teamhelper.glass.R;
import com.teamhelper.glass.databinding.ActivityLoginBinding;
import com.teamhelper.glass.enums.InstructSingle;
import com.teamhelper.glass.manager.IntentManager;
import com.teamhelper.glass.utils.StringUtil;
import com.teamhelper.glass.utils.ToastUtil;
import com.teamhelper.meeting.bean.UserBean;
import com.teamhelper.meeting.interfaces.IMeetingCallback;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.tools.ActivityStackManager;

public class LoginActivity extends RootActivity<ActivityLoginBinding> {
    public static UserBean userBean;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate() {
        instructManager.addInstruct(InstructSingle.INPUT_0, dataBinding.tv0);
        dataBinding.tv0.setOnClickListener(view -> {
            dataBinding.tvUserId.append("0");
        });
        instructManager.addInstruct(InstructSingle.INPUT_1, dataBinding.tv1);
        dataBinding.tv1.setOnClickListener(view -> {
            dataBinding.tvUserId.append("1");
        });
        instructManager.addInstruct(InstructSingle.INPUT_2, dataBinding.tv2);
        dataBinding.tv2.setOnClickListener(view -> {
            dataBinding.tvUserId.append("2");
        });
        instructManager.addInstruct(InstructSingle.INPUT_3, dataBinding.tv3);
        dataBinding.tv3.setOnClickListener(view -> {
            dataBinding.tvUserId.append("3");
        });
        instructManager.addInstruct(InstructSingle.INPUT_4, dataBinding.tv4);
        dataBinding.tv4.setOnClickListener(view -> {
            dataBinding.tvUserId.append("4");
        });
        instructManager.addInstruct(InstructSingle.INPUT_5, dataBinding.tv5);
        dataBinding.tv5.setOnClickListener(view -> {
            dataBinding.tvUserId.append("5");
        });
        instructManager.addInstruct(InstructSingle.INPUT_6, dataBinding.tv6);
        dataBinding.tv6.setOnClickListener(view -> {
            dataBinding.tvUserId.append("6");
        });
        instructManager.addInstruct(InstructSingle.INPUT_7, dataBinding.tv7);
        dataBinding.tv7.setOnClickListener(view -> {
            dataBinding.tvUserId.append("7");
        });
        instructManager.addInstruct(InstructSingle.INPUT_8, dataBinding.tv8);
        dataBinding.tv8.setOnClickListener(view -> {
            dataBinding.tvUserId.append("8");
        });
        instructManager.addInstruct(InstructSingle.INPUT_9, dataBinding.tv9);
        dataBinding.tv9.setOnClickListener(view -> {
            dataBinding.tvUserId.append("9");
        });
        instructManager.addInstruct(InstructSingle.LOGIN, dataBinding.tvLogin);
        dataBinding.tvLogin.setOnClickListener(v -> {
            String userId = dataBinding.tvUserId.getText().toString();
            if (StringUtil.isEmpty(userId)) {
                ToastUtil.showToast(activity, "请输入用户ID");
                return;
            }
            if (StringUtil.isEmpty(Config.APP_KEY)) {
                ToastUtil.showToast(activity, "请配置appKey");
                return;
            }
            if (StringUtil.isEmpty(Config.ACCESS_KEY)) {
                ToastUtil.showToast(activity, "请配置accessKey");
                return;
            }
            if (StringUtil.isEmpty(Config.ACCESS_SECRET)) {
                ToastUtil.showToast(activity, "请配置accessSecret");
                return;
            }
            showLoading();
            //5个小时后token到期
            long timestamp = System.currentTimeMillis() + (5 * 60 * 60 * 1000);
            MeetingManager.exampleLogin(userId, Config.APP_KEY, Config.ACCESS_KEY, Config.ACCESS_SECRET, timestamp, new IMeetingCallback<UserBean>() {
                @Override
                public void onSuccess(UserBean data) {
                    dismissLoading();
                    userBean = data;
                    IntentManager.build(MeetingTodayActivity.class).startActivity(activity);
                    finish();
                }

                @Override
                public void onError(int code, String message) {
                    dismissLoading();
                    ToastUtil.showToast(activity, message);
                }
            });
        });
        dataBinding.instructMenu.setInstructManager(instructManager);
        dataBinding.instructMenu.addInstruct(InstructSingle.UNDO, v -> {
            String value = dataBinding.tvUserId.getText().toString();
            if (StringUtil.isEmpty(value)) return;
            if (value.length() == 1) {
                value = "";
            } else {
                value = value.substring(0, value.length() - 1);
            }
            dataBinding.tvUserId.setText(value);
        });
        dataBinding.instructMenu.addInstruct(InstructSingle.DELETE_CONTENT, v -> {
            dataBinding.tvUserId.setText("");
        });
        dataBinding.instructMenu.addInstruct(InstructSingle.EXIT, v -> {
            ActivityStackManager.getInstance().finishAllActivity();
        });
        slideEventViewManager.setCheckViewListener(dataBinding.tv0);
    }

    @Override
    public void onBack() {
        super.onBack();
        ActivityStackManager.getInstance().finishAllActivity();
    }
}
