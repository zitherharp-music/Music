package com.zitherharpmusic.zhmshort.ui.artist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
    private final FragmentActivity fragmentActivity;
    private final List<Artist> artists;

    public ArtistListAdapter(FragmentActivity fragmentActivity, List<Artist> artists) {
        this.fragmentActivity = fragmentActivity;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.title.setText(artist.getName(Language.VIETNAMESE));
        holder.subtitle.setText(artist.getName(Language.SIMPLIFIED_CHINESE));
        holder.itemView.setOnClickListener(ListenerUtils.launchActivity(fragmentActivity, ArtistActivity.class, artist));
        MainUtils.loadImage(fragmentActivity, holder.photo, artist.getPhotoUrl(PhotoQuality.SMALL));
    }

    @Override
    public int getItemCount() {
        return artists.size();
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
