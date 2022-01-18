package com.zitherharpmusic.zhmshort.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Music;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.video.VideoFullscreenActivity;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.List;

public class ItemGridAdapter extends RecyclerView.Adapter<ItemGridAdapter.ViewHolder> {
    private final FragmentActivity context;
    private final List<? extends Music> musics;

    public ItemGridAdapter(FragmentActivity context, List<? extends Music> musics) {
        this.context = context;
        this.musics = musics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = musics.get(position);
        if (music instanceof Video) {
            MainUtils.loadImage(context, holder.thumbnail, music.getPhotoUrl(PhotoQuality.HQDEFAULT));
            holder.viewCount.setText(((Video) music).getFavouriteCountToString());
            holder.thumbnail.setOnClickListener(ListenerUtils.launchActivity(context, VideoFullscreenActivity.class, musics, position));
        }
    }

    @Override
    public int getItemCount() {
        return musics.size();
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