package com.zitherharpmusic.zhmtelevision.music;

import org.jetbrains.annotations.NotNull;

public class Music {
    public static final String EMPTY_CHARACTER = "";
    public static final String SPACE_CHARACTER = " ";
    public static final String SPLIT_CHARACTER = "/";
    public static final String ENTER_CHARACTER = "\n";

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

    public String getName(@NotNull Language language) {
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
        return null;
    }

    public String getDescription(@NotNull Language language) {
        switch (language) {
            case PINYIN:
            case SIMPLIFIED_CHINESE:
                return simplifiedChineseDescription;
            case TRADITIONAL_CHINESE:
                return traditionalChineseDescription;
            case VIETNAMESE:
                return vietnameseDescription;
        }
        return null;
    }

    public Music setName(@NotNull Language language, String name) {
        switch (language) {
            case VIETNAMESE:
                this.vietnameseName = name;
                return this;
            case PINYIN:
                this.pinyinName = name;
                return this;
            case SIMPLIFIED_CHINESE:
                this.simplifiedChineseName = name;
                return this;
            case TRADITIONAL_CHINESE:
                this.traditionalChineseName = name;
                return this;
        }
        return this;
    }

    public Music setDescription(@NotNull Language language, String description) {
        switch (language) {
            case VIETNAMESE:
                this.vietnameseDescription = description;
                return this;
            case SIMPLIFIED_CHINESE:
                this.simplifiedChineseDescription = description;
                return this;
            case TRADITIONAL_CHINESE:
                this.traditionalChineseDescription = description;
                return this;
        }
        return this;
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

    public String toString(Language language) {
        return id + ": " + getName(language);
    }
}
