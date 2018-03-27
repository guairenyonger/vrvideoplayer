package com.newler.data.utils;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author 刺雒
 * @what
 * @date 2018/3/25
 */

public class BmobUtils {
    public static void init(Application application) {
        BmobConfig config =
                new BmobConfig.Builder(application)
                        .setApplicationId("63485737bb695f3c36271e95b41fffb2")
                        .setConnectTimeout(30)
                        .setUploadBlockSize(1024*1024)
                        .setFileExpiration(2500).build();
        Bmob.initialize(config);
    }
}
