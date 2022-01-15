package com.zitherharpmusic.zhmtelevision.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zitherharpmusic.zhmtelevision.music.MusicHelper;
import com.zitherharpmusic.zhmtelevision.music.MusicProvider;
import com.zitherharpmusic.zhmtelevision.music.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<Song>> playlist;
    private final MutableLiveData<Song> playingSong;
    private final List<Song> songs;

    public MainViewModel() {
        playlist = new MutableLiveData<>();
        playingSong = new MutableLiveData<>();
        songs = new ArrayList<>();

        fill();
        playlist.setValue(songs);
        playingSong.setValue(MusicHelper.getRandom(MusicProvider.getSongs()));
    }

    public LiveData<List<Song>> getPlaylist() {
        return playlist;
    }

    public LiveData<Song> getPlayingSong() {
        return playingSong;
    }

    private void fill() {
        Song song;
        while (songs.size() < 15) {
            song = MusicHelper.getRandom(MusicProvider.getSongs());
            if (songs.contains(song)) continue;
            songs.add(song);
        }
    }

    public void play() {
        playingSong.setValue(songs.get(0));
        songs.remove(0);
        fill();
        playlist.setValue(songs);
    }
}