package com.zitherharpmusic.zhmshort.model;

import java.util.ArrayList;
import java.util.List;

public class Artist extends Music {
    private final String playlistId = "";
    private List<Song> songs;
    private List<Video> videos;

    public Artist(String id) {
        this.id = id;
    }

    public String getPlaylistUrl() {
        return "https://www.youtube.com/playlist?list=" + playlistId;
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

    public List<Video> getVideos() {
        if (videos == null) {
            videos = new ArrayList<>();
            for (Video video : MusicProvider.getVideos()) {
                for (Artist artist : video.getArtists()) {
                    if (artist.getId().equals(id)) {
                        videos.add(video);
                    }
                }
            }
        }
        return videos;
    }
}
