package com.zitherharpmusic.zhmtelevision.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.zitherharpmusic.zhmtelevision.R;
import com.zitherharpmusic.zhmtelevision.music.Language;
import com.zitherharpmusic.zhmtelevision.music.MusicHelper;
import com.zitherharpmusic.zhmtelevision.music.MusicProvider;
import com.zitherharpmusic.zhmtelevision.music.MusicUtils;
import com.zitherharpmusic.zhmtelevision.music.PhotoQuality;
import com.zitherharpmusic.zhmtelevision.music.Song;
import com.zitherharpmusic.zhmtelevision.ui.song.SongListDialog;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainFragment extends Fragment {
    private MainViewModel viewModel;
    private Language language;

    private YouTubePlayerView playerView;
    private Song song1, song2, song3;
    private TextView songId1, songId2, songId3, playlistString, playingSongDetail;
    private ImageView songPhoto1, songPhoto2, songPhoto3, playingSongPhoto;

    @NotNull
    @Contract(" -> new")
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        playerView = view.findViewById(R.id.player_view);
        songId1 = view.findViewById(R.id.song_id1);
        songId2 = view.findViewById(R.id.song_id2);
        songId3 = view.findViewById(R.id.song_id3);
        songPhoto1 = view.findViewById(R.id.song_photo1);
        songPhoto2 = view.findViewById(R.id.song_photo2);
        songPhoto3 = view.findViewById(R.id.song_photo3);
        playlistString = view.findViewById(R.id.playlist);
        playingSongPhoto = view.findViewById(R.id.playing_song_photo);
        playingSongDetail = view.findViewById(R.id.playing_song_detail);

        language = Language.VIETNAMESE;
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        playlistString.setSelected(true);
        viewModel.getPlaylist().observe(getViewLifecycleOwner(), songs -> {
            song1 = songs.get(0);
            song2 = songs.get(1);
            song3 = songs.get(2);
            songId1.setText(song1.getName(language));
            songId2.setText(song2.getName(language));
            songId3.setText(song3.getName(language));
            playlistString.setText(MusicHelper.toString(songs.subList(0, 14), language));
            MusicUtils.loadImage(songPhoto1, song1.getPhotoUrl(PhotoQuality.MQDEFAULT));
            MusicUtils.loadImage(songPhoto2, song2.getPhotoUrl(PhotoQuality.MQDEFAULT));
            MusicUtils.loadImage(songPhoto3, song3.getPhotoUrl(PhotoQuality.MQDEFAULT));
            playlistString.setOnClickListener(v -> SongListDialog.newInstance(songs).show(getParentFragmentManager(), v.toString()));
        });
        viewModel.getPlayingSong().observe(getViewLifecycleOwner(), song -> {
            playingSongDetail.setText(MusicHelper.getSongDetail(song, language));
            MusicUtils.loadImage(playingSongPhoto, song.getPhotoUrl(PhotoQuality.SDDEFAULT));
        });
        playerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            youTubePlayer.loadVideo(viewModel.getPlayingSong().getValue().getId(), 0);
            youTubePlayer.addListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                }

                @Override
                public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {
                    if (playerState.equals(PlayerConstants.PlayerState.ENDED)) {
                        viewModel.play();
                        youTubePlayer.loadVideo(viewModel.getPlayingSong().getValue().getId(), 0);
                    }
                }

                @Override
                public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

                }

                @Override
                public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

                }

                @Override
                public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

                }

                @Override
                public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                }

                @Override
                public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                }
            });
        });
    }
}