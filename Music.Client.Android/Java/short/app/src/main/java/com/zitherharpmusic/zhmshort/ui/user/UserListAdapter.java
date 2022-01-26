package com.zitherharpmusic.zhmshort.ui.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Artist;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private final List<Artist> artists;

    public UserListAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.artistTitle.setText(artist.getName(Language.VIETNAMESE));
        holder.artistSubtitle.setText(artist.getName(Language.SIMPLIFIED_CHINESE));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView artistPhoto;
        protected TextView artistTitle;
        protected TextView artistSubtitle;

        protected ViewHolder(View view) {
            super(view);
            artistPhoto = view.findViewById(R.id.photo);
            artistTitle = view.findViewById(R.id.title);
            artistSubtitle = view.findViewById(R.id.subtitle);
        }
    }
}
