package com.zitherharpmusic.zhmshort.model;

import java.util.ArrayList;
import java.util.List;

public class Song extends Music {
    private final String artistIds;

    private List<Video> videos;
    private List<Artist> artists;

    public Song(String id, String artistIds) {
        this.id = id;
        this.artistIds = artistIds;
    }

    public List<Video> getVideos() {
        if (videos == null) {
            videos = new ArrayList<>();
            for (Video video : MusicProvider.getVideos()) {
                for (Song song : video.getSongs()) {
                    if (song.getId().equals(id)) {
                        videos.add(video);
                    }
                }
            }
        }
        return videos;
    }

    public List<Artist> getArtists() {
        if (artists == null) {
            artists = new ArrayList<>();
            for (Artist artist : MusicProvider.getArtists()) {
                for (String artistId : artistIds.split(SPLIT_CHARACTER)) {
                    if (artist.getId().equals(artistId)) {
                        artists.add(artist);
                    }
                }
            }
        }
        return artists;
    }

    public String toString(Language language) {
        return getName(language) + " - " + MusicUtils.getNames(getArtists(), language);
    }
}
