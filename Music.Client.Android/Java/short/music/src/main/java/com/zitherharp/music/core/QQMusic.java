package com.zitherharp.music.core;

import com.zitherharp.music.model.Artist;
import com.zitherharp.music.model.Audio;

import java.util.ArrayList;
import java.util.List;

public abstract class QQMusic extends Spreadsheet {
    private List<Audio> audios;

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

    public List<Audio> getAudios() {
        if (audios == null) {
            audios = new ArrayList<>();
            for (Audio audio : Repository.getAudios()) {
                for (Artist artist : audio.getArtists()) {
                    if (artist.Id.equals(Id)) {
                        audios.add(audio);
                    }
                }
            }
        }
        return audios;
    }
}
