package com.zitherharpmusic.zhmtelevision.ui.song;

import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmtelevision.R;
import com.zitherharpmusic.zhmtelevision.music.Language;
import com.zitherharpmusic.zhmtelevision.music.MusicHelper;
import com.zitherharpmusic.zhmtelevision.music.MusicUtils;
import com.zitherharpmusic.zhmtelevision.music.PhotoQuality;
import com.zitherharpmusic.zhmtelevision.music.Song;

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
        holder.subtitle.setText(MusicHelper.getNames(song.getArtists(), Language.VIETNAMESE));
        MusicUtils.loadImage(holder.photo, song.getPhotoUrl(PhotoQuality.MQDEFAULT));
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
