package com.zitherharpmusic.zhmshort.ui.song;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.MusicUtils;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.ui.artist.ArtistActivity;
import com.zitherharpmusic.zhmshort.model.User;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        getSupportActionBar().hide();
        // TODO: initialize variables
        Song song = (Song) getIntent().getSerializableExtra(SongActivity.class.getName());
        User user = new User(this);
        // TODO: find views
        ImageView photo = findViewById(R.id.photo);
        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        TextView description = findViewById(R.id.description);
        ImageButton followButton = findViewById(R.id.follow);
        ImageButton shareButton = findViewById(R.id.share);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        // TODO: set values
        title.setText(song.getName(Language.VIETNAMESE));
        subtitle.setText(song.getName(Language.SIMPLIFIED_CHINESE));
        description.setText(MusicUtils.getNames(song.getArtists(), Language.VIETNAMESE));
        description.setOnClickListener(ListenerUtils.launchActivity(this, ArtistActivity.class, song.getArtists().get(0)));
        shareButton.setOnClickListener(ListenerUtils.launchShareIntent(this, song.getShareUrl()));
        if (!MusicUtils.contains(user.getSongs(), song)) {
            followButton.setTag(getString(R.string.follow));
            followButton.setImageResource(R.drawable.ic_favourite_border_24);
        } else {
            followButton.setTag(getString(R.string.unfollow));
            followButton.setImageResource(R.drawable.ic_favourite_24);
        }
        followButton.setOnClickListener(v -> {
            if (followButton.getTag().equals(getString(R.string.follow))) {
                user.put(User.SONG_IDS, song.getId());
                followButton.setTag(getString(R.string.unfollow));
                followButton.setImageResource(R.drawable.ic_favourite_24);
                Toast.makeText(this, "Đã thêm yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                user.remove(User.SONG_IDS, song.getId());
                followButton.setTag(getString(R.string.follow));
                followButton.setImageResource(R.drawable.ic_favourite_border_24);
                Toast.makeText(this, "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
        MainUtils.loadImage(this, photo, song.getPhotoUrl(PhotoQuality.MQDEFAULT));
        photo.setOnClickListener(v -> {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ImageView photoView = new ImageView(this);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            MainUtils.loadImage(this, photoView, song.getPhotoUrl(PhotoQuality.MAXRESDEFAULT));
            ad.setView(photoView);
            ad.show();
        });
        // TODO: others
        SongAdapter songAdapter = new SongAdapter(this, song);
        viewPager.setAdapter(songAdapter);
        songAdapter.attach(tabLayout, viewPager);
    }
}
