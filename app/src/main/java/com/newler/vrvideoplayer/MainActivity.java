package com.newler.vrvideoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newler.data.bean.VideoInfo;
import com.newler.data.utils.JsonConvertUtils;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String json = JsonConvertUtils.fileToString(getAssets().open("vr.json"));
            List list = JsonConvertUtils.jsonToList(VideoInfo.class, json);
            JsonConvertUtils.uploadList(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
