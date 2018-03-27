package com.newler.data.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * @author 刺雒
 * @what
 * @date 2018/3/25
 */

public class VideoInfo extends BmobObject {
    private String name;
    private String thumbnail;
    private List<String> mp4;
    private List<String> m3u8;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getMp4() {
        return mp4;
    }

    public void setMp4(List<String> mp4) {
        this.mp4 = mp4;
    }

    public List<String> getM3u8() {
        return m3u8;
    }

    public void setM3u8(List<String> m3u8) {
        this.m3u8 = m3u8;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
