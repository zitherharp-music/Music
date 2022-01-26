package com.zitherharpmusic.zhmshort.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.zitherharpmusic.zhmshort.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.zitherharpmusic.zhmshort.model.Music.*;

public class User {
    public static final String ID = "userId";
    public static final String NAME = "userName";
    public static final String SONG_IDS = "userSongIds";
    public static final String VIDEO_IDS = "userVideoIds";
    public static final String ARTIST_IDS = "userArtistIds";

    private final Context context;
    private final SharedPreferences.Editor editor;

    private final List<Song> songs = new ArrayList<>();
    private final List<Video> videos = new ArrayList<>();
    private final List<Artist> artists = new ArrayList<>();

    public User(Context context) {
        this.context = context;
        editor = context.getSharedPreferences(User.class.getName(), Context.MODE_PRIVATE).edit();
        if (get(ID).equals(EMPTY_CHARACTER)) {
            editor.putString(ID, UUID.randomUUID().toString()).apply();
            editor.putString(NAME, context.getString(R.string.app_name)).apply();
        }
    }

    public String get(String key) {
        return context.getSharedPreferences(User.class.getName(), Context.MODE_PRIVATE).getString(key, EMPTY_CHARACTER);
    }

    public void put(String key, String id) {
        String value = get(key);
        if (value.contains(id)) return;
        if (!value.equals(EMPTY_CHARACTER)) {
            editor.putString(key, value + SPLIT_CHARACTER + id + SPLIT_CHARACTER);
        } else {
            editor.putString(key, id + SPLIT_CHARACTER);
        }
        editor.apply();
    }

    public void replace(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void remove(String key, String id) {
        String value = get(key);
        if (value.equals(EMPTY_CHARACTER)) return;
        editor.putString(key, value.replace(id + SPLIT_CHARACTER, EMPTY_CHARACTER)).apply();
    }

    public boolean isLoggedIn() {
        return !get(ID).equals(EMPTY_CHARACTER);
    }

    public List<Song> getSongs() {
        songs.clear();
        String[] songIds = get(SONG_IDS).split(SPLIT_CHARACTER);
        for (String songId : songIds) {
            for (Song song : MusicProvider.getSongs()) {
                if (song.getId().equals(songId)) {
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    public List<Video> getVideos() {
        videos.clear();
        String[] videoIds = get(VIDEO_IDS).split(SPLIT_CHARACTER);
        for (String videoId : videoIds) {
            for (Video video : MusicProvider.getVideos()) {
                if (video.getId().equals(videoId)) {
                    videos.add(video);
                }
            }
        }
        return videos;
    }

    public List<Artist> getArtists() {
        artists.clear();
        String[] artistIds = get(ARTIST_IDS).split(SPLIT_CHARACTER);
        for (String artistId : artistIds) {
            for (Artist artist : MusicProvider.getArtists()) {
                if (artist.getId().equals(artistId)) {
                    artists.add(artist);
                }
            }
        }
        return artists;
    }
}
