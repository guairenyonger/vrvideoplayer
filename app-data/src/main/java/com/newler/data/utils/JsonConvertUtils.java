package com.newler.data.utils;

import com.alibaba.fastjson.JSON;
import com.newler.data.rxbmob.RxBmob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author 刺雒
 * @what
 * @date 2018/3/25
 */

public class JsonConvertUtils {
    public static String fileToString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = "";
        StringBuilder res = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            res.append(line);
        }
        return new String(res.toString().getBytes(), "utf-8");
    }

    public static List jsonToList(Class c, String json) {
        return JSON.parseArray(json, c);
    }

    public static void uploadList(List list) {
        RxBmob.getInstance().saveList(list).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });
    }
}
