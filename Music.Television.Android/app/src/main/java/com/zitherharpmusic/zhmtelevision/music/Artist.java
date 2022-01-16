package com.zitherharpmusic.zhmtelevision.music;

import java.util.ArrayList;
import java.util.List;

public class Artist extends Music {
    private List<Song> songs;

    public Artist(String id) {
        this.id = id;
    }

    public List<Song> getSongs() {
        if (songs == null) {
            songs = new ArrayList<>();
            for (Song song : MusicProvider.getSongs()) {
                for (Artist artist : song.getArtists()) {
                    if (artist.getId().equals(id)) {
                        songs.add(song);
                    }
                }
            }
        }
        return songs;
    }
}
