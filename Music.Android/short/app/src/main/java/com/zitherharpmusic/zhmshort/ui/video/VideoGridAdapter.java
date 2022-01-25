package com.zitherharpmusic.zhmshort.ui.video;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.List;

public class VideoGridAdapter extends RecyclerView.Adapter<VideoGridAdapter.ViewHolder> {
    private final FragmentActivity context;
    private final List<Video> videos;

    public VideoGridAdapter(FragmentActivity context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.viewCount.setText(video.getFavouriteCountToString());
        MainUtils.loadImage(context, holder.thumbnail, video.getPhotoUrl(PhotoQuality.HQDEFAULT));
        holder.thumbnail.setOnClickListener(ListenerUtils.launchActivity(context, VideoFullscreenActivity.class, videos, position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected TextView viewCount;

        public ViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewCount = view.findViewById(R.id.view_count);
        }
    }
}
