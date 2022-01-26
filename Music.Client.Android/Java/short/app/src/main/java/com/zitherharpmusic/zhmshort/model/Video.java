package com.zitherharpmusic.zhmshort.model;

import com.zitherharpmusic.zhmshort.model.MusicProvider;
import com.zitherharpmusic.zhmshort.model.Music;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.Song;

import java.util.ArrayList;
import java.util.List;

public class Video extends Music {
    public static final int ARTIST_ID = 1;
    public static final int SONG_ID = 2;

    private final String artistIds;
    private final String songIds;
    public int viewCount = 0;
    public int favouriteCount = 0;
    public int commentCount = 0;

    private List<Song> songs;
    private List<Artist> artists;

    public Video(String id, String artistIds, String songIds) {
        this.id = id;
        this.artistIds = artistIds;
        this.songIds = songIds;
    }

    public String getSongIds() {
        return songIds;
    }

    public void setStatistics(int viewCount, int favouriteCount, int commentCount) {
        this.viewCount = viewCount;
        this.favouriteCount = favouriteCount;
        this.commentCount = commentCount;
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

    public List<Song> getSongs() {
        if (songs == null) {
            songs = new ArrayList<>();
            for (Song song : MusicProvider.getSongs()) {
                for (String songId : songIds.split(SPLIT_CHARACTER)) {
                    if (song.getId().equals(songId)) {
                        songs.add(song);
                    }
                }
            }

        }
        return songs;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public String getFavouriteCountToString() {
        return favouriteCount + "";
    }

    public String getCommentCountToString() {
        return commentCount + "";
    }
}
