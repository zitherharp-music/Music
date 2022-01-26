package com.zitherharpmusic.android.model;

import com.zitherharpmusic.android.core.QQMusic;

import java.util.ArrayList;
import java.util.List;

public class Artist extends QQMusic {
    private List<Audio> audios;
    private List<Short> shorts;

    @Override
    public String getImageUrl(Image image) {
        return String.format("https://y.qq.com/music/photo_new/T001R%sM000%s.jpg", image.getSize(), Id);
    }

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
