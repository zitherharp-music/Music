package com.zitherharpmusic.zhmtelevision.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Song extends Music {
    public static final int DURATION = 6;

    private final String artistIds;
    private final int duration;

    private List<Artist> artists;

    public Song(String id, String artistIds, int duration) {
        this.id = id;
        this.artistIds = artistIds;
        this.duration = duration;
    }

    public List<Artist> getArtists() {
        if (artists == null) {
            artists = new ArrayList<>();
            for (String artistId : artistIds.split(SPLIT_CHARACTER)) {
                for (Artist artist : MusicProvider.getArtists()) {
                    if (artist.getId().equals(artistId)) {
                        artists.add(artist);
                        break;
                    }
                }
            }
        }
        return artists;
    }

    public String getDuration() {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
