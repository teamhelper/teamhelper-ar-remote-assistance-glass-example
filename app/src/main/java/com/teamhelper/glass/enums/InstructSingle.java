package com.teamhelper.glass.enums;

import com.teamhelper.base.interfaces.IInstructSingle;
import com.teamhelper.glass.utils.StringUtil;

import java.util.Locale;

public enum InstructSingle implements IInstructSingle {
    LOGIN("deng lu", "登录", "Login"),
    INPUT_0("shu ru ling", "输入零", "Input zero"),
    INPUT_1("shu ru yi", "输入一", "Input one"),
    INPUT_2("shu ru er", "输入二", "Input two"),
    INPUT_3("shu ru san", "输入三", "Input three"),
    INPUT_4("shu ru si", "输入四", "Input four"),
    INPUT_5("shu ru wu", "输入五", "Input five"),
    INPUT_6("shu ru liu", "输入六", "Input six"),
    INPUT_7("shu ru qi", "输入七", "Input seven"),
    INPUT_8("shu ru ba", "输入八", "Input eight"),
    INPUT_9("shu ru jiu", "输入九", "Input nine"),
    UNDO("che xiao", "撤销", "Undo"),
    DELETE_CONTENT("shan chu nei rong", "删除内容", "Delete content"),
    BACK("fan hui", "返回", "Return"),
    HISTORY_MEETING("li shi xie zuo", "历史协作", "History cooperation"),
    LAUNCHER_MEETING("fa qi xie zuo", "发起协作", "Launcher cooperation"),
    CONFIRM("que ding", "确定", "Confirm"),
    PREVIOUS_PAGE("shang yi ye", "上一页", "Previous page"),
    NEXT_PAGE("xia yi ye", "下一页", "Next page"),
    EXIT("tui chu", "退出", "Exit"),
    ;
    private final String en;
    private final String pinyin;
    private final String zh;

    InstructSingle(String pinyin, String zh, String en) {
        this.pinyin = pinyin;
        this.zh = zh.replaceAll("\\s", "");
        this.en = en;
    }

    @Override
    public String getInstruct() {
        String language = Locale.getDefault().getLanguage();
        if (StringUtil.equals(language, "zh")) {
            return zh;
        } else if (StringUtil.equals(language, "en")) {
            return en;
        } else {
            return zh;
        }
    }

    @Override
    public String getPinYin() {
        return pinyin;
    }
}
