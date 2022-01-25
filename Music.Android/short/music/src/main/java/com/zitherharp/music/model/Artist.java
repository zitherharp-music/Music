package com.zitherharp.music.model;

import com.zitherharp.music.core.QQMusic;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Artist extends QQMusic {
    private List<Short> shorts;

    @Override
    public String getImageUrl(@NotNull Image image) {
        return String.format("https://y.qq.com/music/photo_new/T001R%sM000%s.jpg", image.getSize(), Id);
    }
}
