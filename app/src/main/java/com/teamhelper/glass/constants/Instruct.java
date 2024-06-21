package com.teamhelper.glass.constants;

import com.mst.basics.instruct.base.InstructSingle;

public interface Instruct {
    InstructSingle LOGIN = new InstructSingle("deng lu", "登录", "Login");

    InstructSingle INPUT_0 = new InstructSingle("shu ru ling", "输入零", "Input zero");

    InstructSingle INPUT_1 = new InstructSingle("shu ru yi", "输入一", "Input one");

    InstructSingle INPUT_2 = new InstructSingle("shu ru er", "输入二", "Input two");

    InstructSingle INPUT_3 = new InstructSingle("shu ru san", "输入三", "Input three");

    InstructSingle INPUT_4 = new InstructSingle("shu ru si", "输入四", "Input four");

    InstructSingle INPUT_5 = new InstructSingle("shu ru wu", "输入五", "Input five");

    InstructSingle INPUT_6 = new InstructSingle("shu ru liu", "输入六", "Input six");

    InstructSingle INPUT_7 = new InstructSingle("shu ru qi", "输入七", "Input seven");

    InstructSingle INPUT_8 = new InstructSingle("shu ru ba", "输入八", "Input eight");

    InstructSingle INPUT_9 = new InstructSingle("shu ru jiu", "输入九", "Input nine");

    InstructSingle UNDO = new InstructSingle("che xiao", "撤销", "Undo");

    InstructSingle DELETE_CONTENT = new InstructSingle("shan chu nei rong", "删除内容", "Delete content");

    InstructSingle BACK = new InstructSingle("fan hui", "返回", "Return");

    InstructSingle HISTORY_MEETING = new InstructSingle("li shi xie zuo", "历史协作", "History cooperation");

    InstructSingle LAUNCHER_MEETING = new InstructSingle("fa qi xie zuo", "发起协作", "Launcher cooperation");

    InstructSingle CONFIRM = new InstructSingle("que ding", "确定", "Confirm");

    InstructSingle PREVIOUS_PAGE = new InstructSingle("shang yi ye", "上一页", "Previous page");

    InstructSingle NEXT_PAGE = new InstructSingle("xia yi ye", "下一页", "Next page");

    InstructSingle EXIT = new InstructSingle("tui chu", "退出", "Exit");
}
