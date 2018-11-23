package com.xoshop.widget.TimeRangePicker.config;


import com.xoshop.R;
import com.xoshop.widget.TimeRangePicker.data.Type;

/**
 * 默认配置, 类型, 颜色, 文字
 *
 * @author C.L. Wang
 */
public class DefaultConfig {
    public static final Type TYPE = Type.ALL; // 全部类型

    public static final int TOOLBAR_BKG_COLOR = R.color.white; // 背景颜色
    public static final int ITEM_SELECTOR_LINE = R.color.text_select; // 选择线颜色
    public static final int ITEM_SELECTOR_RECT = R.color.white; // 选择框颜色


    public static final int TOOLBAR_TV_COLOR = 0xFFFFFFFF;
    public static final int TV_NORMAL_COLOR = R.color.textcolor; // 默认文字颜色
    public static final int TV_SELECTOR_COLOR = R.color.black; // 选中文字颜色

    public static final int TV_SIZE = 12; // 尺寸

    public static final boolean CYCLIC = true; // 循环

    public static final int MAX_LINE = 5; // 最大行数, 依据控件样式

    // 默认文案
    public static String CANCEL = "取消";
    public static String SURE = "确定";
    public static String TITLE = "请选择日期";
    public static String YEAR = "  ";
    public static String MONTH = "  ";
    public static String DAY = "  ";
    public static String HOUR = "时";
    public static String MINUTE = "分";
}
