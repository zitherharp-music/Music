package com.zitherharpmusic.zhmshort.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.zitherharpmusic.zhmshort.model.Music;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;

import java.io.Serializable;
import java.util.List;

public class ListenerUtils {
    public static YouTubePlayerListener requireYoutubePlayerListener(String videoId) {
        return new YouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {
                if (playerState.equals(PlayerConstants.PlayerState.ENDED)) {
                    youTubePlayer.play();
                }
            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(YouTubePlayer youTubePlayer, String s) {

            }

            @Override
            public void onApiChange(YouTubePlayer youTubePlayer) {

            }
        };
    }

    public static View.OnClickListener launchShareIntent(Context context, String text) {
        return v -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.setType("text/plain");
            context.startActivity(shareIntent);
        };
    }

    public static View.OnClickListener launchActivity(Context context, Class<? extends Activity> cls,
                                                      List<? extends Music> musics, int position) {
        return v -> {
            Intent intent = new Intent(context, cls);
            Class<?>[] classes = cls.getClasses();
            intent.putExtra(classes[0].getName(), (Serializable) musics);
            intent.putExtra(classes[1].getName(), position);
            context.startActivity(intent);
        };
    }

    public static View.OnClickListener launchActivity(Context context, Class<? extends Activity> cls, Music music) {
        return v -> {
            Intent intent = new Intent(context, cls);
            intent.putExtra(cls.getName(), music);
            context.startActivity(intent);
        };
    }

    public static void watchPhoto(FragmentActivity context, Music music) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ImageView photoView = new ImageView(context);
        MainUtils.loadImage(context, photoView, music.getPhotoUrl(PhotoQuality.LARGE));
        ad.setView(photoView);
        ad.show();
    }

    public static View.OnClickListener copyToClipboard(Context context, String text) {
        return v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(context.getPackageName(), text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Sao chép thành công", Toast.LENGTH_SHORT).show();
        };
    }
}
