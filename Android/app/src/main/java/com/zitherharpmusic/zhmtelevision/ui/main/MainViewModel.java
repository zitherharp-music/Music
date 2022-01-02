package com.zitherharpmusic.zhmtelevision.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zitherharpmusic.zhmtelevision.music.MusicProvider;
import com.zitherharpmusic.zhmtelevision.music.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<Song>> playlist;
    private final List<Song> songs;
    private final Random random;

    public MainViewModel() {
        playlist = new MutableLiveData<>();
        songs = new ArrayList<>();
        random = new Random();
        fill();
        playlist.setValue(songs);
    }

    public LiveData<List<Song>> getPlaylist() {
        return playlist;
    }

    private void fill() {
        while (songs.size() < 15) {
            Song song = MusicProvider.getSongs().get(random.nextInt(MusicProvider.getSongs().size()));
            if (!songs.contains(song)) {
                songs.add(song);
            }
        }
    }

    public void play() {
        songs.remove(0);
        fill();
        playlist.setValue(songs);
    }
}