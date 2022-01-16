package com.zitherharpmusic.zhmshort.ui.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.MusicUtils;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.User;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.video.VideoFullscreenActivity;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final Fragment fragment;
    private final List<Video> videos;

    public NotificationAdapter(Fragment fragment, User user) {
        this.fragment = fragment;
        videos = new ArrayList<>();
        for (Artist artist : user.getArtists()) {
            videos.addAll(artist.getVideos());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.title.setText(String.format("%s đã tải lên một video mới", MusicUtils.getNames(video.getArtists(), Language.VIETNAMESE)));
//        holder.subtitle.setText(String.format("\"%s\"", video.getName(Language.SIMPLIFIED_CHINESE)));
        MainUtils.loadImage(fragment.requireActivity(), holder.photo, video.getArtists().get(0).getPhotoUrl(PhotoQuality.SMALL));
        holder.itemView.setOnClickListener(ListenerUtils.launchActivity(
                fragment.requireContext(), VideoFullscreenActivity.class, videos, position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView photo;
        protected TextView title;
        protected TextView subtitle;

        protected ViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.photo);
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
        }
    }
}
