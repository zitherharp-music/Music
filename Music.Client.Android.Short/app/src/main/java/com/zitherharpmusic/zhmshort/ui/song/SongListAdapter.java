package com.zitherharpmusic.zhmshort.ui.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {
    private final FragmentActivity fragmentActivity;
    private final List<Song> songs;

    public SongListAdapter(FragmentActivity fragmentActivity, List<Song> songs) {
        this.fragmentActivity = fragmentActivity;
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.title.setText(song.getName(Language.VIETNAMESE));
        holder.subtitle.setText(song.getName(Language.SIMPLIFIED_CHINESE));
        holder.itemView.setOnClickListener(ListenerUtils.launchActivity(fragmentActivity, SongActivity.class, song));
        MainUtils.loadImage(fragmentActivity, holder.photo, song.getPhotoUrl(PhotoQuality.MQDEFAULT));
    }

    @Override
    public int getItemCount() {
        return songs.size();
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
