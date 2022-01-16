package com.zitherharpmusic.zhmshort.model;

import java.io.Serializable;

public class Music implements Serializable {
    public static final String EMPTY_CHARACTER = "";
    public static final String SPLIT_CHARACTER = "/";

    public static final int PRIMARY_ID = 0;
    public static final int REFERENCE_ID = 1;
    public static final int VIETNAMESE_NAME = 2;
    public static final int SIMPLIFIED_CHINESE_NAME = 3;
    public static final int TRADITIONAL_CHINESE_NAME = 4;
    public static final int PINYIN_NAME = 5;
    public static final int VIETNAMESE_DESCRIPTION = 6;
    public static final int SIMPLIFIED_CHINESE_DESCRIPTION = 7;
    public static final int TRADITIONAL_CHINESE_DESCRIPTION = 8;

    protected String id;
    protected String vietnameseName;
    protected String pinyinName;
    protected String simplifiedChineseName;
    protected String traditionalChineseName;
    protected String vietnameseDescription;
    protected String simplifiedChineseDescription;
    protected String traditionalChineseDescription;

    public String getId() {
        return id;
    }

    public String getName(Language language) {
        switch (language) {
            case PINYIN:
                return pinyinName;
            case SIMPLIFIED_CHINESE:
                return simplifiedChineseName;
            case TRADITIONAL_CHINESE:
                return traditionalChineseName;
            case VIETNAMESE:
                return vietnameseName;
        }
        throw new NullPointerException();
    }

    public String getDescription(Language language) {
        switch (language) {
            case PINYIN:
            case SIMPLIFIED_CHINESE:
                return simplifiedChineseDescription;
            case TRADITIONAL_CHINESE:
                return traditionalChineseDescription;
            case VIETNAMESE:
                return vietnameseDescription;
        }
        throw new NullPointerException();
    }

    public String getPhotoUrl(PhotoQuality photoQuality) {
        if (photoQuality == PhotoQuality.SMALL || photoQuality == PhotoQuality.MEDIUM || photoQuality == PhotoQuality.LARGE) {
            return String.format("https://y.qq.com/music/photo_new/T001R%sM000%s.jpg", photoQuality.getSize(), id);
        } else {
            return String.format("https://i.ytimg.com/vi/%s/%s.jpg", id, photoQuality.name().toLowerCase());
        }
    }

    public String getShareUrl() {
        return "https://youtu.be/" + id;
    }

    public void setName(String vietnameseName, String pinyinName,
                        String simplifiedChineseName, String traditionalChineseName) {
        this.vietnameseName = vietnameseName;
        this.pinyinName = pinyinName;
        this.simplifiedChineseName = simplifiedChineseName;
        this.traditionalChineseName = traditionalChineseName;
    }

    public void setDescription(String vietnameseDescription,
                               String simplifiedChineseDescription, String traditionalChineseDescription) {
        this.vietnameseDescription = vietnameseDescription;
        this.simplifiedChineseDescription = simplifiedChineseDescription;
        this.traditionalChineseDescription = traditionalChineseDescription;
    }
}
