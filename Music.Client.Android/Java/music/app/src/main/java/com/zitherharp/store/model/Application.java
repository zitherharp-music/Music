package com.zitherharp.store.model;

public class Application {
    public String id;
    public String iconId, imageId;
    public String name, description, version;
    public String link;
    public int size;

    public String getChannelUrl() {
        return "https://www.youtube.com/channel/" + id;
    }

    public String getIconUrl() {
        return "https://yt3.ggpht.com/" + iconId;
    }

}
