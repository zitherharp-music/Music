package com.zitherharpmusic.zhmshort.model;

public enum PhotoQuality {
    DEFAULT("120x90"),
    MQDEFAULT("320x180"),
    HQDEFAULT("480x360"),
    SDDEFAULT("640x480"),
    MAXRESDEFAULT("1280x720"),
    SMALL("300x300"),
    MEDIUM("500x500"),
    LARGE("800x800");

    PhotoQuality(String size) {
        this.size = size;
    }

    String size;

    public String getSize() {
        return size;
    }
}
