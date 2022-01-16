package com.zitherharpmusic.zhmshort.ui.video;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.MusicUtils;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.artist.ArtistActivity;
import com.zitherharpmusic.zhmshort.ui.artist.ArtistListDialog;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.ui.song.SongActivity;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.List;

public class VideoFullscreenAdapter extends RecyclerView.Adapter<VideoFullscreenAdapter.ViewHolder> {
    private final FragmentActivity context;
    private final List<Video> videos;

    public VideoFullscreenAdapter(FragmentActivity context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_fullscreen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Video
        Video video = videos.get(position);
        holder.videoName.setText(video.getName(Language.VIETNAMESE));
        holder.shareButton.setOnClickListener(ListenerUtils.launchShareIntent(context, video.getShareUrl()));
        holder.videoView.getYouTubePlayerWhenReady(youTubePlayer -> {
            youTubePlayer.loadVideo(video.getId(), 0);
            youTubePlayer.pause();
            youTubePlayer.addListener(ListenerUtils.requireYoutubePlayerListener(video.getId()));
        });
        holder.favouriteCount.setText(video.getFavouriteCountToString());
        holder.commentCount.setText(video.getCommentCountToString());
        // TODO: Artist
        List<Artist> artists = video.getArtists();
        if (video.getArtists().size() == 0) {
            Toast.makeText(context, video.getId(), Toast.LENGTH_SHORT).show();
            return;
        } else {
            MainUtils.loadImage(context, holder.artistImage, artists.get(0).getPhotoUrl(PhotoQuality.SMALL));
            holder.artistName.setText(MusicUtils.getNames(artists, Language.VIETNAMESE));
            if (artists.size() == 1) {
                Artist artist = artists.get(0);
                holder.artistImage.setOnClickListener(ListenerUtils.launchActivity(context, ArtistActivity.class, artist));
                holder.artistName.setOnClickListener(ListenerUtils.launchActivity(context, ArtistActivity.class, artist));
            } else {
                View.OnClickListener showArtistDialog = v ->
                        ArtistListDialog.newInstance(artists).show(context.getSupportFragmentManager(), "?");
                holder.artistImage.setOnClickListener(showArtistDialog);
                holder.artistName.setOnClickListener(showArtistDialog);
            }
        }
        // TODO: Song
        holder.songName.setSelected(true);
        List<Song> songs = video.getSongs();
        if (songs.size() > 0) {
            holder.songName.setText(songs.get(0).toString(Language.VIETNAMESE));
            holder.songName.setOnClickListener(ListenerUtils.launchActivity(context, SongActivity.class, songs.get(0)));
        }
    }
    @Override
    public int getItemCount() {
        return videos.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected final YouTubePlayerView videoView;
        protected final ImageView artistImage;
        protected final TextView artistName, videoName, songName;
        protected final TextView favouriteCount, commentCount;
        protected final ImageButton favouriteButton, commentButton, shareButton;

        public ViewHolder(View view) {
            super(view);
            videoView = view.findViewById(R.id.video_view);
            artistName = view.findViewById(R.id.artist_name);
            videoName = view.findViewById(R.id.video_name);
            songName = view.findViewById(R.id.song_name);
            artistImage = view.findViewById(R.id.artist_image);
            favouriteCount = view.findViewById(R.id.favourite_count);
            commentCount = view.findViewById(R.id.comment_count);
            favouriteButton = view.findViewById(R.id.favourite_button);
            commentButton = view.findViewById(R.id.comment_button);
            shareButton = view.findViewById(R.id.share_button);
        }
    }
}
