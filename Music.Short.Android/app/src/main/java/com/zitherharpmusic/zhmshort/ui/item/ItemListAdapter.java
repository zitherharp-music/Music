package com.zitherharpmusic.zhmshort.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.Music;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.ui.artist.ArtistActivity;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.ui.song.SongActivity;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder>  {
    private final FragmentActivity context;
    private final List<? extends Music> musics;

    public ItemListAdapter(FragmentActivity context, List<? extends Music> musics) {
        this.context = context;
        this.musics = musics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = musics.get(position);
        holder.title.setText(music.getName(Language.VIETNAMESE));
        holder.subtitle.setText(music.getName(Language.SIMPLIFIED_CHINESE));
        if (music instanceof Song) {
            holder.photoView.setRadius(0);
            MainUtils.loadImage(context, holder.photo, music.getPhotoUrl(PhotoQuality.DEFAULT));
            holder.itemView.setOnClickListener(ListenerUtils.launchActivity(context, SongActivity.class, music));
        } else if (music instanceof Artist) {
            holder.photoView.setRadius(40);
            MainUtils.loadImage(context, holder.photo, music.getPhotoUrl(PhotoQuality.SMALL));
            holder.itemView.setOnClickListener(ListenerUtils.launchActivity(context, ArtistActivity.class, music));
        }
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView photoView;
        protected ImageView photo;
        protected TextView title;
        protected TextView subtitle;

        protected ViewHolder(View view) {
            super(view);
            photoView = view.findViewById(R.id.photo_view);
            photo = view.findViewById(R.id.photo);
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
        }
    }
}
