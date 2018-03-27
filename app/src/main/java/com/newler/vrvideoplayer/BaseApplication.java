package com.newler.vrvideoplayer;

import android.app.Application;
import android.content.Context;

import com.newler.data.utils.BmobUtils;

/**
 * @author 刺雒
 * @what
 * @date 2018/3/25
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BmobUtils.init(this);
    }

    public static Context getContext() {
        return getContext();
    }
}
