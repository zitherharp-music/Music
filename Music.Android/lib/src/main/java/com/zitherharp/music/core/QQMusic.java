package com.zitherharpmusic.android.core;

public abstract class QQMusic extends Spreadsheet {
    public enum Image {
        SMALL("300x300"),
        MEDIUM("500x500"),
        LARGE("800x800");
        String size;
        Image(String size) {
            this.size = size;
        }
        public String getSize() {
            return size;
        }
    }

    public abstract String getImageUrl(Image image);
}
