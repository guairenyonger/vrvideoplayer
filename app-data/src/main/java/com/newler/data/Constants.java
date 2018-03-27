package com.newler.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jingbin on 2016/11/26.
 * 固定参数
 */

public class Constants {

    public static final String NIGHT_SKIN = "night.skin";
    public static final String KEY_MODE_NIGHT = "mode-night";
    public static final String UPDATE_USER_INFO = "update_user_info";
    // 首页没日推荐缓存
    public static String GANK_ALL = "gank_all";
    // 安卓数据
    public static String GANK_ANDROID = "gank_android";
    // 订制数据
    public static String GANK_CUSTOM = "gank_custom";
    // 热映缓存
    public static String ONE_HOT_MOVIE = "one_hot_movie";
    // 保存每日推荐轮播图url
    public static String BANNER_PIC = "banner_pic";
    // 保存每日推荐轮播图的跳转数据
    public static String BANNER_PIC_DATA = "banner_pic_data";
    // 保存每日推荐recyclerview内容
    public static String EVERYDAY_CONTENT = "everyday_content";
    // 缓存妹子
    public static String GANK_MEIZI = "gank_meizi";
    // 干货订制类别
    public static String GANK_CALA = "gank_cala";

    public static final  int SINGLE_SAVE = 0;
    public static final int SINGLE_DELETE = 1;
    public static final int SINGLE_UPDATE = 2;
    public static final int LIST_SAVE = 3;
    public static final int LIST_DELETE = 4;
    public static final int LIST_UPDATE = 5;

    @IntDef({SINGLE_SAVE, SINGLE_DELETE, SINGLE_UPDATE, LIST_SAVE, LIST_DELETE, LIST_UPDATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OperationStatus {

    }

}
