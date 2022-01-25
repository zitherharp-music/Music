package com.zitherharpmusic.android.core;

public abstract class Youtube extends Spreadsheet {
    public enum Image {
        DEFAULT("120x90"),
        MQDEFAULT("320x180"),
        HQDEFAULT("480x360"),
        SDDEFAULT("640x480"),
        MAXRESDEFAULT("1280x720");
        String size;
        Image(String size) {
            this.size = size;
        }
        public String getSize() {
            return size;
        }
    }

    public String getImageUrl(Image image) {
        return String.format("https://i.ytimg.com/vi/%s/%s.jpg", Id, image.name().toLowerCase());
    }

    public String getShareUrl() {
        return "https://youtu.be/" + Id;
    }
}
